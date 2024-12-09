package com.example.localappcj

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private val touristLocations = listOf(
        GeoPoint(31.7398, -106.4869), // Lugar 1: Centro Cultural Paso del Norte
        GeoPoint(31.6904, -106.4245), // Lugar 2: Catedral de Ciudad Juárez
        GeoPoint(31.7433, -106.4415)  // Lugar 3: Parque Chamizal
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Configuration.getInstance().load(requireContext(), requireActivity().getPreferences(0))
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Inicializar el mapa
        mapView = view.findViewById(R.id.map_view)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(GeoPoint(31.7398, -106.4869)) // Ciudad Juárez

        // Configuración de botones
        val btnCenter = view.findViewById<Button>(R.id.btn_centrar_mapa)
        val btnMarkers = view.findViewById<Button>(R.id.btn_marcadores)

        btnCenter.setOnClickListener { centerMap() }
        btnMarkers.setOnClickListener { showMarkers() }

        return view
    }

    private fun centerMap() {
        mapView.controller.animateTo(GeoPoint(31.7398, -106.4869))
    }

    private fun showMarkers() {
        touristLocations.forEach { location ->
            val marker = Marker(mapView)
            marker.position = location
            marker.title = "Zona Turística"
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)
        }
        mapView.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
    }
}
