package cl.ti3042.medidor_prof

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import cl.ti3042.medidor_prof.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            goToDashboard()
        }

        binding.btnLogin.setOnClickListener { iniciarSesion() }
        binding.btnRegistrar.setOnClickListener { registrarUsuario() }
    }

    private fun iniciarSesion() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { goToDashboard() }
            .addOnFailureListener {
                Toast.makeText(this, "Error al iniciar sesión: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun registrarUsuario() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.length < 6) {
            Toast.makeText(this, "Contraseña mínima de 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { goToDashboard() }
            .addOnFailureListener {
                Toast.makeText(this, "Error al registrar: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun goToDashboard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}