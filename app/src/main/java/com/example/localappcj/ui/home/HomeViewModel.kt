package com.example.localappcj.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Explora los mejores lugares de Ciudad Juárez"
    }
    val text: LiveData<String> = _text

    private val _lugares = MutableLiveData<List<Lugar>>()
    val lugares: LiveData<List<Lugar>> = _lugares

    init {
        cargarLugares()  // Cargar datos locales
    }

    private fun cargarLugares() {
        // Datos locales o estáticos, no se necesita una API
        _lugares.value = listOf(
            Lugar("Centro Histórico", "https://viajerosvagabundos.com/centro-de-ciudad-juarez/"),
            Lugar("Parque El Chamizal", "https://www.expedia.mx/Parque-Publico-Federal-El-Chamizal-Ciudad-Juarez.d6320197.Guia-Turistica"),
            Lugar("Plaza de la Mexicanidad", "https://example.com/plaza-mexicanidad"),
            Lugar("Catedral de Ciudad Juárez", "https://www.expedia.mx/Monumento-A-La-Mexicanidad-Ciudad-Juarez.d6320167.Guia-Turistica"),
            Lugar("Parque Central", "https://www.expedia.mx/Parque-Central-Ciudad-Juarez.d6164754.Guia-Turistica"),
            Lugar("Museo de la Rodadora", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d4946477-Reviews-La_Rodadora_Espacio_Interactivo-Ciudad_Juarez_Northern_Mexico.html"),
            Lugar("Parque Extremo Trepachanga", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d9806049-Reviews-Trepa_Changa-Ciudad_Juarez_Northern_Mexico.html"),
            Lugar("Parque Eco 200", "https://www.elheraldodejuarez.com.mx/local/eco-2000-tierra-sin-ley-noticias-de-ciudad-juarez-4704567.html"),
            Lugar("Las Dunas de Bilbao", "https://www.tripadvisor.com.mx/Attraction_Review-g8863309-d8848266-Reviews-Dunas_de_Bilbao-Viesca_Northern_Mexico.html"),
            // Museos y cultura
            Lugar("Museo de la Revolución en la Frontera", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d4340841-Reviews-Museo_De_La_Revolucion_En_La_Frontera-Ciudad_Juarez_Northern_Mexico.html"),
            Lugar("Paso del Norte Cultural Center", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d10535766-Reviews-Centro_Cultural_Paso_del_Norte-Ciudad_Juarez_Northern_Mexico.html"),
            // Monumentos
            Lugar("Monumento a Benito Juárez", "https://escapadas.mexicodesconocido.com.mx/atractivos/monumento-a-benito-juarez/"),
            // Otros
            Lugar("Mercado Juárez", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d10272074-Reviews-Mercado_Juarez-Ciudad_Juarez_Northern_Mexico.html"),
            Lugar("Plaza de las Misiones", "https://www.tripadvisor.com.mx/Attraction_Review-g150780-d6474366-Reviews-Plaza_Las_Misiones-Ciudad_Juarez_Northern_Mexico.html"),
            Lugar("Samalayuca", "https://www.tripadvisor.com.mx/Tourism-g11810240-Samalayuca_Northern_Mexico-Vacations.html"),
            Lugar("Puente Internacional Santa Fe", "https://reportedepuentes.com.mx/"),
            Lugar("Estadio Olímpico Benito Juárez", "https://www.expedia.mx/Estadio-Olimpico-Benito-Juarez-Ciudad-Juarez.d633444529474191360.Guia-Turistica")
        )
    }
}
