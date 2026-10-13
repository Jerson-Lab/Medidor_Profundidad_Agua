package cl.ti3042.medidor_prof

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cl.ti3042.medidor_prof.databinding.ActivityAlertasBinding

class AlertasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertasBinding
    private val firebaseRepo = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRepo.observarUmbralAlerta { umbral ->
            runOnUiThread { binding.seekUmbral.progress = umbral }
        }

        binding.btnGuardarUmbral.setOnClickListener {
            firebaseRepo.actualizarUmbralAlerta(binding.seekUmbral.progress)
        }
    }
}