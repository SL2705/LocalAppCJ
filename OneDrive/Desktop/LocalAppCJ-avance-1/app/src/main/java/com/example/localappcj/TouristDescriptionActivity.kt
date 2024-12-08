package com.example.localappcj

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TouristDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_description)

        val title = findViewById<TextView>(R.id.tourist_title)
        val description = findViewById<TextView>(R.id.tourist_description)

        // Obtener los detalles del lugar turístico desde el Intent
        val touristName = intent.getStringExtra("TOURIST_NAME")
        val touristDescription = intent.getStringExtra("TOURIST_DESCRIPTION")

        // Asegurarse de que los datos no sean nulos antes de usarlos
        title.text = touristName ?: "Nombre no disponible"
        description.text = touristDescription ?: "Descripción no disponible"
    }
}
