package com.example.localappcj.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.localappcj.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerViewZonas: RecyclerView// Contenedor donde se mostrarán las zonas
    private lateinit var btnMostrarZonas: Button // Botón para mostrar las zonas
    private lateinit var btnCentrarMapa: Button // Botón para centrar el mapa

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        // Inicializa el contenedor de zonas y el botón de "Mostrar Zonas"
        recyclerViewZonas = rootView.findViewById(R.id.recycler_view_zonas)
        btnMostrarZonas = rootView.findViewById(R.id.btn_marcadores)
        btnCentrarMapa = rootView.findViewById(R.id.btn_centrar_mapa)

        // Configurar el fragmento del mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configura el clic del botón "Mostrar Zonas"
        btnMostrarZonas.setOnClickListener {
            mostrarZonas()
        }

        // Configura el clic del botón "Centrar Mapa"
        btnCentrarMapa.setOnClickListener {
            centrarMapa()
        }

        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Habilitar controles básicos
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        // Verificar permisos para mostrar ubicación actual
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            // Solicitar permisos si no están otorgados
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        // Agregar marcadores al mapa
        agregarMarcadores()
    }

    private fun centrarMapa() {
        // Coordenadas de ejemplo (puedes usar coordenadas de tu ubicación actual)
        val defaultLocation = LatLng(31.7398, -106.4843) // Ciudad Juárez
        val zoomLevel = 12f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, zoomLevel))
    }

    private fun agregarMarcadores() {
        val lugares = listOf(
            // Lugares turísticos principales
            Lugar("Centro Histórico", LatLng(31.742650, -106.486183)),
            Lugar("Parque El Chamizal", LatLng(31.740204, -106.445667)),
            Lugar("Plaza de la Mexicanidad", LatLng(31.731759, -106.440765)),
            Lugar("Catedral de Ciudad Juárez", LatLng(31.739850, -106.485359)),

            // Parques y áreas recreativas
            Lugar("Parque Central", LatLng(31.693933, -106.401276)),
            Lugar("Parque Extremo Trepachanga", LatLng(31.768034, -106.430870)),
            Lugar("Parque Eco 200", LatLng(31.733020, -106.422770)),
            Lugar("Las Dunas de Bilbao", LatLng(31.459972, -106.297401)),

            // Museos y cultura
            Lugar("Museo de la Revolución en la Frontera", LatLng(31.739207, -106.483710)),
            Lugar("Museo de la Rodadora", LatLng(31.693533, -106.404978)),
            Lugar("Paso del Norte Cultural Center", LatLng(31.743250, -106.486570)),

            // Monumentos
            Lugar("Monumento a Benito Juárez", LatLng(31.742950, -106.484950)),

            // Otros
            Lugar("Mercado Juárez", LatLng(31.739040, -106.482520)),
            Lugar("Plaza de las Misiones", LatLng(31.670455, -106.418663)),
            Lugar("Samalayuca", LatLng(31.460250, -106.295807)),
            Lugar("Puente Internacional Santa Fe", LatLng(31.756142, -106.487896)),
            Lugar("Estadio Olímpico Benito Juárez", LatLng(31.692450, -106.424739))
        )

        // Añadir cada marcador al mapa
        for (lugar in lugares) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(lugar.latLng)
                    .title(lugar.nombre)
            )
        }
    }

    private fun mostrarZonas() {
        // Cambia la visibilidad del RecyclerView de las zonas
        if (recyclerViewZonas.visibility == View.GONE) {
            recyclerViewZonas.visibility = View.VISIBLE // Muestra el RecyclerView de zonas
            setupRecyclerView()
        } else {
            recyclerViewZonas.visibility = View.GONE // Oculta el RecyclerView de zonas
        }
    }

    private fun setupRecyclerView() {
        val lugares = listOf(
            Lugar("Centro Histórico", LatLng(31.742650, -106.486183)),
            Lugar("Parque El Chamizal", LatLng(31.740204, -106.445667)),
            Lugar("Plaza de la Mexicanidad", LatLng(31.731759, -106.440765)),
            Lugar("Catedral de Ciudad Juárez", LatLng(31.739850, -106.485359)),

            // Parques y áreas recreativas
            Lugar("Parque Central", LatLng(31.693933, -106.401276)),
            Lugar("Parque Extremo Trepachanga", LatLng(31.768034, -106.430870)),
            Lugar("Parque Eco 200", LatLng(31.733020, -106.422770)),
            Lugar("Las Dunas de Bilbao", LatLng(31.459972, -106.297401)),

            // Museos y cultura
            Lugar("Museo de la Revolución en la Frontera", LatLng(31.739207, -106.483710)),
            Lugar("Museo de la Rodadora", LatLng(31.693533, -106.404978)),
            Lugar("Paso del Norte Cultural Center", LatLng(31.743250, -106.486570)),

            // Monumentos
            Lugar("Monumento a Benito Juárez", LatLng(31.742950, -106.484950)),

            // Otros
            Lugar("Mercado Juárez", LatLng(31.739040, -106.482520)),
            Lugar("Plaza de las Misiones", LatLng(31.670455, -106.418663)),
            Lugar("Samalayuca", LatLng(31.460250, -106.295807)),
            Lugar("Puente Internacional Santa Fe", LatLng(31.756142, -106.487896)),
            Lugar("Estadio Olímpico Benito Juárez", LatLng(31.692450, -106.424739))
        )

        val adapter = ZonasAdapter(lugares) { lugar ->
            // Al seleccionar una zona, centrar el mapa en la ubicación de la zona seleccionada
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugar.latLng, 15f))
        }

        recyclerViewZonas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewZonas.adapter = adapter
    }

    data class Lugar(val nombre: String, val latLng: LatLng)


    class ZonasAdapter(
        private val lugares: List<Lugar>,
        private val onItemClick: (Lugar) -> Unit
    ) : RecyclerView.Adapter<ZonasAdapter.ZonaViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZonaViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_zona, parent, false)
            return ZonaViewHolder(view)
        }

        override fun onBindViewHolder(holder: ZonaViewHolder, position: Int) {
            val lugar = lugares[position]
            holder.nombreTextView.text = lugar.nombre
            holder.itemView.setOnClickListener { onItemClick(lugar) }
        }

        override fun getItemCount(): Int = lugares.size

        class ZonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombreTextView: TextView = itemView.findViewById(R.id.zona_nombre)
        }
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
