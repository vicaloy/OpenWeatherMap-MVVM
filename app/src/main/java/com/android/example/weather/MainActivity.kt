package com.android.example.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.android.example.weather.vo.City
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnMapReadyCallback, HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var googleMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
        mapFragment.getMapAsync(this)
        mapFragment.view?.visibility= View.GONE
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    fun addMarker(city: City){
        mapFragment.view?.visibility= View.VISIBLE
        googleMap.clear()
        val coords=LatLng(city.latlong.lat, city.latlong.lon)
        googleMap.addMarker(MarkerOptions().position(coords).title(city.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coords))
    }



}
