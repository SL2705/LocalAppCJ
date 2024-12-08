package com.example.localappcj

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.localappcj.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()
            val name = binding.signupName.text.toString()
            val gender = binding.signupGender.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                name.isNotEmpty() && gender.isNotEmpty()) {
                if (password == confirmPassword) {
                    if (gender.equals("Masculino", ignoreCase = true) ||
                        gender.equals("Femenino", ignoreCase = true) ||
                        gender.equals("Otro", ignoreCase = true)) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    firebaseAuth.signInWithEmailAndPassword(email, password)
                                                    .addOnCompleteListener(this) { loginTask ->
                                                        if (loginTask.isSuccessful) {
                                                            // Redirigir a la actividad principal
                                                            val intent = Intent(this, MainActivity::class.java)
                                                            startActivity(intent)
                                                            finish()  // Cerrar la actividad de registro
                                                        } else {
                                                            Toast.makeText(
                                                                this,
                                                                "Error al iniciar sesión: ${loginTask.exception?.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }

                                } else {
                                    // Mostrar error si la creación del usuario falla
                                    Toast.makeText(
                                        this,
                                        "Error al registrar el usuario: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Por favor, ingrese un género válido: Masculino, Femenino u Otro",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Redirección al login
        binding.LoginRedirectText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}
