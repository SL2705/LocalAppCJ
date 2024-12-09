package com.example.localappcj

import android.content.Intent
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

    // Lista de ubicaciones turísticas
    private val touristLocations = listOf(
        GeoPoint(31.7398, -106.4869), // Lugar 1: Centro Cultural Paso del Norte
        GeoPoint(31.6904, -106.4245), // Lugar 2: Catedral de Ciudad Juárez
        GeoPoint(31.7433, -106.4415)  // Lugar 3: Parque Chamizal
    )

    // Nombres de los lugares turísticos
    private val touristNames = listOf(
        "Centro Cultural Paso del Norte",
        "Catedral de Ciudad Juárez",
        "Parque Chamizal"
    )

    // Descripciones de los lugares turísticos
    private val touristDescriptions = listOf(
        "Este es un centro cultural que ofrece diversas exposiciones y actividades.",
        "La Catedral de Ciudad Juárez es uno de los edificios más antiguos de la ciudad.",
        "Parque Chamizal es un parque donde se celebran festivales y actividades al aire libre."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(requireContext(), requireActivity().getPreferences(0))
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Inicializar el mapa
        mapView = view.findViewById(R.id.map_view)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(GeoPoint(31.7398, -106.4869)) // Ciudad Juárez

        // Configuración de botones
        val btnCenter = view.findViewById<Button>(R.id.btn_center)
        val btnMarkers = view.findViewById<Button>(R.id.btn_markers)

        btnCenter.setOnClickListener { centerMap() }
        btnMarkers.setOnClickListener { showMarkers() }

        return view
    }

    private fun centerMap() {
        mapView.controller.animateTo(GeoPoint(31.7398, -106.4869))
    }

    private fun showMarkers() {
        touristLocations.forEachIndexed { index, location ->
            val marker = Marker(mapView)
            marker.position = location
            marker.title = touristNames[index] // Título del marcador con el nombre del lugar
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            // Establecer el Listener para abrir la actividad con los detalles
            marker.setOnMarkerClickListener(object : Marker.OnMarkerClickListener {
                fun onMarkerClick(marker: Marker): Boolean {
                    // Pasar los detalles del lugar turístico a la nueva actividad
                    val intent = Intent(requireContext(), TouristDescriptionActivity::class.java)
                    intent.putExtra("TOURIST_NAME", touristNames[index])
                    intent.putExtra("TOURIST_DESCRIPTION", touristDescriptions[index])
                    startActivity(intent)
                    return true // Indicamos que hemos manejado el clic
                }

                override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {
                    TODO("Not yet implemented")
                }
            })

            mapView.overlays.add(marker)
        }
        mapView.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
    }
}
