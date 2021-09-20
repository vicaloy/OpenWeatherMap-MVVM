package com.android.example.github.presentation.weather

import android.os.Bundle
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.android.example.github.AppExecutors
import com.android.example.github.R
import com.android.example.github.presentation.binding.FragmentDataBindingComponent
import com.android.example.github.databinding.WeatherFragmentBinding
import com.android.example.github.di.Injectable
import com.android.example.github.util.autoCleared
import javax.inject.Inject
import android.view.*
import androidx.lifecycle.Observer
import com.android.example.github.vo.RegisterUser


class WeatherFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val weatherViewModel: WeatherViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<WeatherFragmentBinding>()

    private val params by navArgs<WeatherFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<WeatherFragmentBinding>(
            inflater,
            R.layout.weather_fragment,
            container,
            false
        )

        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = weatherViewModel

        setHasOptionsMenu(true)

        weatherViewModel.loadWeather(1185241)

        weatherViewModel.logoutSuccess.observe(viewLifecycleOwner,
            { logoutSuccess ->
                if (logoutSuccess) {
                    requireActivity().onBackPressed()
                }

            })

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
