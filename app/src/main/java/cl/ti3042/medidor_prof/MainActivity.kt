package cl.ti3042.medidor_prof

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cl.ti3042.medidor_prof.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firebaseRepo = FirebaseRepository()
    private var simulador: SimuladorArduino? = null

    private val prefs by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rol = prefs.getString("rol", null)
        if (rol == null) {
            elegirRol()
        } else {
            aplicarRol(rol)
        }

        firebaseRepo.observarNivelActual { nivel ->
            runOnUiThread { binding.tvNivelAgua.text = "$nivel%" }
        }

        binding.btnHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        binding.btnAlertas.setOnClickListener {
            startActivity(Intent(this, AlertasActivity::class.java))
        }
    }

    private fun elegirRol() {
        AlertDialog.Builder(this)
            .setTitle("¿Qué rol cumple este dispositivo?")
            .setMessage("Elige \"Emisor\" solo en UN AVD (simula el sensor).")
            .setPositiveButton("Emisor") { _, _ -> guardarRol("EMISOR") }
            .setNegativeButton("Receptor") { _, _ -> guardarRol("RECEPTOR") }
            .setCancelable(false)
            .show()
    }

    private fun guardarRol(rol: String) {
        prefs.edit().putString("rol", rol).apply()
        aplicarRol(rol)
    }

    private fun aplicarRol(rol: String) {
        binding.tvRol.text = "Rol: $rol"
        if (rol == "EMISOR") {
            simulador = SimuladorArduino { nivel -> firebaseRepo.subirLectura(nivel) }
            simulador?.iniciar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        simulador?.detener()
    }
}