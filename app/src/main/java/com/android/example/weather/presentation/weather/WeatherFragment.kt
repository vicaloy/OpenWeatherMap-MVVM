package com.android.example.weather.presentation.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.android.example.weather.R
import com.android.example.weather.databinding.WeatherFragmentBinding
import com.android.example.weather.di.Injectable
import com.android.example.weather.util.autoCleared
import com.android.example.weather.vo.City
import javax.inject.Inject
import android.widget.AdapterView

import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import com.android.example.weather.MainActivity
import com.android.example.weather.presentation.binding.FragmentDataBindingComponent
import com.android.example.weather.vo.LatLong
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class WeatherFragment : Fragment(), Injectable {

    private val REQUEST_CODE = 101

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val weatherViewModel: WeatherViewModel by viewModels {
        viewModelFactory
    }

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<WeatherFragmentBinding>()

    private var hasPermission = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.weather_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = weatherViewModel

        setHasOptionsMenu(true)

        weatherViewModel.getCitiesList()

        setUI()


    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.loading.visibility = View.VISIBLE
            binding.cardView.visibility = View.GONE
            binding.citySpinner.visibility = View.GONE
        } else {
            binding.loading.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
            binding.citySpinner.visibility = View.VISIBLE
        }
    }

    private fun setUI() {
        weatherViewModel.logoutSuccess.observe(viewLifecycleOwner,
            { logoutSuccess ->
                if (logoutSuccess) {
                    requireActivity().onBackPressed()
                } else {
                    makeToast("Logout error")
                }
            })

        weatherViewModel.currentWeather.observe(viewLifecycleOwner, { currentWeather ->
            if (currentWeather != null) {
                binding.cardView.visibility = View.VISIBLE
                binding.txtCityCountry.text = currentWeather.cityCountry
                binding.txtDateTime.text = currentWeather.date
                binding.txtHumidity.text = currentWeather.humidity
                binding.txtWind.text = currentWeather.wind
                binding.txtTemperature.text = currentWeather.temp
                binding.txtPressure.text = currentWeather.pressure
                binding.ivWeatherCondition.background =
                    ResourcesCompat.getDrawable(resources, currentWeather.res, null);

            } else {
                binding.cardView.visibility = View.GONE
                makeToast("No data for weather")
            }

            showProgress(false)

        })

        weatherViewModel.citiesList.observe(viewLifecycleOwner, { cities ->
            if (cities != null) {
                setCityListSpinner(cities)
            }
        })

        binding.btnLocal.setOnClickListener{
            if(hasPermission){
                workHasPermission()
            }else{
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
        } else {
            hasPermission = true
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED}) {
                hasPermission = true
                workHasPermission()
            } else {
                makeToast("Permissions Denied")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun workHasPermission(){
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let {
                weatherViewModel.getWeatherByLatLon(location.latitude.toString(), location.longitude.toString())
                val city =  City(0, "Your location", "", LatLong(location.longitude, location.latitude))
                (activity as MainActivity).addMarker(city)
            }
        }



    }


    private fun setCityListSpinner(citiesList: List<City>) {

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            citiesList.map { it.name }
        )

        binding.citySpinner.adapter = arrayAdapter


        binding.citySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                showProgress(true)

                val selectedCity = citiesList[binding.citySpinner.selectedItemPosition]
                weatherViewModel.getWeatherByCity(selectedCity.id)

                (activity as MainActivity).addMarker(selectedCity)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

    }


    private fun makeToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> weatherViewModel.logout()

        }
        return true
    }
}
