package com.example.localappcj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val touristLocations = listOf(
        LatLng(31.7398, -106.4869), // Lugar 1: Centro Cultural Paso del Norte
        LatLng(31.6904, -106.4245), // Lugar 2: Catedral de Ciudad Juárez
        LatLng(31.7433, -106.4415)  // Lugar 3: Parque Chamizal
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Inicializar el mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configuración de botones
        val btnCenter = view.findViewById<Button>(R.id.btn_centrar_mapa)
        val btnMarkers = view.findViewById<Button>(R.id.btn_marcadores)

        btnCenter.setOnClickListener { centerMap() }
        btnMarkers.setOnClickListener { showMarkers() }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(31.7398, -106.4869), 15f)) // Ciudad Juárez
    }

    private fun centerMap() {
        map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(31.7398, -106.4869)))
    }

    private fun showMarkers() {
        map.clear() // Limpiar marcadores antes de agregar nuevos
        touristLocations.forEach { location ->
            map.addMarker(MarkerOptions().position(location).title("Zona Turística"))
        }
    }
}