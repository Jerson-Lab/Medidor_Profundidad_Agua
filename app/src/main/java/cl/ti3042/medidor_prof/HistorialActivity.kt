package cl.ti3042.medidor_prof

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import cl.ti3042.medidor_prof.databinding.ActivityHistorialBinding

class HistorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ref = FirebaseDatabase.getInstance().reference.child("lecturas")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children
                    .sortedByDescending { it.key }
                    .take(20)
                    .map { "${it.key}: ${it.getValue(Int::class.java)}%" }

                binding.listHistorial.adapter = ArrayAdapter(
                    this@HistorialActivity,
                    android.R.layout.simple_list_item_1,
                    items
                )
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}