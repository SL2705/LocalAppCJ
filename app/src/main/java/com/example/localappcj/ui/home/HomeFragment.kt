package com.example.localappcj.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.localappcj.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar el Spinner
        val categorySpinner: Spinner = binding.categoryFilterSpinner

        // Observamos los lugares desde el ViewModel
        homeViewModel.lugares.observe(viewLifecycleOwner) { lugares ->
            // Extraer los nombres de los lugares para llenar el Spinner
            val nombresLugares = lugares.map { it.nombre }

            // Crear el ArrayAdapter con los nombres de los lugares
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                nombresLugares
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            categorySpinner.adapter = adapter

            // Configurar el listener para detectar la selección
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val lugarSeleccionado = lugares[position]
                    val url = lugarSeleccionado.url
                    // Mostrar el mensaje de confirmación
                    mostrarMensajeConfirmacion(lugarSeleccionado.nombre, url)
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // No se seleccionó nada, no hacer nada
                }
            }
        }

        return root
    }

    private fun mostrarMensajeConfirmacion(nombreLugar: String, url: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Abrir Enlace")
        builder.setMessage("¿Deseas abrir el enlace de $nombreLugar en tu navegador?")
        builder.setPositiveButton("Sí") { dialog, which ->
            abrirEnlace(url) // Si acepta, abre el enlace
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Si no acepta, no hacer nada
        }
        builder.show()
    }

    private fun abrirEnlace(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
