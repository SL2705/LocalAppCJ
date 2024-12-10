package com.example.localappcj.ui.notifications

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.localappcj.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.localappcj.LoginActivity

class NotificationsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Referencia a la base de datos de Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el diseño fragment_settings.xml
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtener el usuario actual de Firebase Auth
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Si el usuario está autenticado, obtener su nombre, correo y edad
            val username = currentUser.displayName ?: "Usuario no disponible"
            val email = currentUser.email ?: "Correo no disponible"

            // Actualizar el nombre y el correo en la UI
            binding.textUsername.text = username
            binding.textEmail.text = email

            // Obtener la edad del usuario desde Firestore
            val userId = currentUser.uid
            db.collection("usuarios").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Supongamos que la edad está almacenada bajo el campo "edad"
                        val edad = document.getLong("edad") ?: "Edad no disponible"
                        binding.textAge.text = edad.toString()
                    } else {
                        binding.textAge.text = "Edad no disponible"
                    }
                }
                .addOnFailureListener {
                    binding.textAge.text = "Error al obtener la edad"
                }
        } else {
            // Si no hay usuario autenticado, mostrar un mensaje
            binding.textUsername.text = "No has iniciado sesión"
            binding.textEmail.text = "No disponible"
            binding.textAge.text = "No disponible"
        }

        // Lógica para el botón de cerrar sesión
        binding.buttonLogout.setOnClickListener {
            // Cerrar sesión de Firebase
            FirebaseAuth.getInstance().signOut()

            // Redirigir a la pantalla de inicio de sesión
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

            // Finalizar la actividad actual para evitar que el usuario regrese a esta pantalla con el botón de atrás
            activity?.finish()
        }

        // Lógica para el Switch que permite cambiar el tema
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        // Establecer el estado inicial del Switch según el tema actual
        binding.switchDarkMode.isChecked = isDarkMode

        // Cambiar el tema cuando el usuario cambia el estado del Switch
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Activar el modo oscuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Activar el modo claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
