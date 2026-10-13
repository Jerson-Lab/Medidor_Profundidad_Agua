package cl.ti3042.medidor_prof

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class SimuladorArduino(private val onNuevaLectura: (Int) -> Unit) {

    private val handler = Handler(Looper.getMainLooper())
    private var nivelActual = 70
    private var corriendo = false

    private val runnable = object : Runnable {
        override fun run() {
            if (!corriendo) return
            nivelActual = (nivelActual + Random.nextInt(-4, 3)).coerceIn(0, 100)
            onNuevaLectura(nivelActual)
            handler.postDelayed(this, 3000L)
        }
    }

    fun iniciar() {
        if (corriendo) return
        corriendo = true
        handler.post(runnable)
    }

    fun detener() {
        corriendo = false
        handler.removeCallbacks(runnable)
    }
}