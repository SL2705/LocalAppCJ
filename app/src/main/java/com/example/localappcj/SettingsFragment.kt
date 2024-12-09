package com.example.localappcj

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate


class SettingsFragment : Fragment() {

    private lateinit var themeSwitch: Switch
    private lateinit var notificationsSwitch: Switch
    private lateinit var logoutButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_settings, container, false)



        // Inicializar vistas
        themeSwitch = view.findViewById(R.id.theme_switch)
        notificationsSwitch = view.findViewById(R.id.notifications_switch)
        logoutButton = view.findViewById(R.id.logout_button)

        // Leer preferencias almacenadas
        val isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode", false)
        themeSwitch.isChecked = isDarkModeEnabled

        // Aplicar el tema según el valor almacenado
        setTheme(isDarkModeEnabled)

        // Establecer los listeners para las acciones
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Cambiar el estado de tema y guardar en SharedPreferences
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
            setTheme(isChecked)
        }

        // Listener para el switch de notificaciones (ejemplo)
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Guardar el estado de las notificaciones
            sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply()
        }

        // Listener para el botón de cerrar sesión
        logoutButton.setOnClickListener {
            // Aquí puedes implementar la lógica para cerrar sesión (por ejemplo, limpiando las preferencias)
            sharedPreferences.edit().clear().apply()
            // Llamar a una actividad de inicio de sesión o a cualquier acción de cierre de sesión
            // Ejemplo: startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return view
    }

    /**
     * Cambia el tema de la aplicación.
     */
    private fun setTheme(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
