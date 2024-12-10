package com.example.localappcj.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
            Lugar("Centro Histórico", LatLng(31.739253561630473, -106.4870409552749)),
            Lugar("Parque El Chamizal", LatLng(31.75920181852354, -106.46219629283134)),
            Lugar("Plaza de la Mexicanidad", LatLng(31.756386770602845, -106.4401384968854)),
            Lugar("Catedral de Ciudad Juárez", LatLng(31.740256089921207, -106.48654055291723)),

            // Parques y áreas recreativas
            Lugar("Parque Central", LatLng (31.68724876366143, -106.42695221855345)),
            Lugar("Parque Extremo Trepachanga", LatLng(31.689908124499688, -106.52032966156766)),
            Lugar("Parque Eco 200", LatLng(31.6539245336635, -106.39625095599284)),
            Lugar("Las Dunas de Bilbao", LatLng(31.651283435334452, -106.3896121243793)),

            // Museos y cultura
            Lugar("Museo de la Revolución en la Frontera", LatLng(31.7390238865449, -106.48410149990805)),
            Lugar("Museo de la Rodadora", LatLng(31.69003781611001, -106.42813933204813)),
            Lugar("Paso del Norte Cultural Center", LatLng(31.745065231916747, -106.44591133405756)),

            // Monumentos
            Lugar("Monumento a Benito Juárez", LatLng(31.735565519068032, -106.47905993204668)),

            // Otros
            Lugar("Mercado Juárez", LatLng(31.739662809105194, -106.47831553020112)),
            Lugar("Plaza de las Misiones", LatLng(31.694319045666063, -106.41088545475802)),
            Lugar("Samalayuca", LatLng(31.34195387354509, -106.47650567950242)),
            Lugar("Puente Internacional Santa Fe", LatLng(31.74994025914671, -106.48565066532043)),
            Lugar("Estadio Olímpico Benito Juárez", LatLng(31.752894626468258, -106.46824034554089))
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
            Lugar("Centro Histórico", LatLng(31.739253561630473, -106.4870409552749)),
            Lugar("Parque El Chamizal", LatLng(31.75920181852354, -106.46219629283134)),
            Lugar("Plaza de la Mexicanidad", LatLng(31.756386770602845, -106.4401384968854)),
            Lugar("Catedral de Ciudad Juárez", LatLng(31.740256089921207, -106.48654055291723)),

            // Parques y áreas recreativas
            Lugar("Parque Central", LatLng (31.68724876366143, -106.42695221855345)),
            Lugar("Parque Extremo Trepachanga", LatLng(31.689908124499688, -106.52032966156766)),
            Lugar("Parque Eco 200", LatLng(31.6539245336635, -106.39625095599284)),
            Lugar("Las Dunas de Bilbao", LatLng(31.651283435334452, -106.3896121243793)),

            // Museos y cultura
            Lugar("Museo de la Revolución en la Frontera", LatLng(31.7390238865449, -106.48410149990805)),
            Lugar("Museo de la Rodadora", LatLng(31.69003781611001, -106.42813933204813)),
            Lugar("Paso del Norte Cultural Center", LatLng(31.745065231916747, -106.44591133405756)),

            // Monumentos
            Lugar("Monumento a Benito Juárez", LatLng(31.735565519068032, -106.47905993204668)),

            // Otros
            Lugar("Mercado Juárez", LatLng(31.739662809105194, -106.47831553020112)),
            Lugar("Plaza de las Misiones", LatLng(31.694319045666063, -106.41088545475802)),
            Lugar("Samalayuca", LatLng(31.34195387354509, -106.47650567950242)),
            Lugar("Puente Internacional Santa Fe", LatLng(31.74994025914671, -106.48565066532043)),
            Lugar("Estadio Olímpico Benito Juárez", LatLng(31.752894626468258, -106.46824034554089))
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
                .inflate(R.layout.item_lugar, parent, false)
            return ZonaViewHolder(view)
        }

        override fun onBindViewHolder(holder: ZonaViewHolder, position: Int) {
            val lugar = lugares[position]
            holder.nombreTextView.text = lugar.nombre
            holder.itemView.setOnClickListener { onItemClick(lugar) }
        }

        override fun getItemCount(): Int = lugares.size

        class ZonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombreTextView: TextView = itemView.findViewById(R.id.text_nombre)
        }
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
