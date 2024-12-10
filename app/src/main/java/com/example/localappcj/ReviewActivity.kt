package com.example.localappcj

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Referencias a los elementos de la vista
        val reviewText: EditText = findViewById(R.id.review_text)
        val submitButton: Button = findViewById(R.id.submit_review_button)

        // Configurar listener para el botón de enviar reseña
        submitButton.setOnClickListener {
            val reviewContent = reviewText.text.toString().trim()

            if (reviewContent.isEmpty()) {
                // Mostrar un mensaje si el campo de reseña está vacío
                Toast.makeText(this, "Por favor, escribe tu reseña antes de enviarla.", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí puedes implementar la lógica para guardar o enviar la reseña
                // Por ejemplo, podrías enviarla a un servidor o guardarla localmente
                sendReview(reviewContent)

                // Limpiar el campo de texto después de enviar
                reviewText.text.clear()

                // Mostrar confirmación al usuario
                Toast.makeText(this, "¡Reseña enviada con éxito!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendReview(reviewContent: String) {
        // TODO: Implementar la lógica para enviar la reseña
        // Por ejemplo, realizar una solicitud a un servidor o guardar en una base de datos local
        println("Reseña enviada: $reviewContent")
    }
}
