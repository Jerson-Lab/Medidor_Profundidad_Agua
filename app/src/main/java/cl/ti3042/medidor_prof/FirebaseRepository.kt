package cl.ti3042.medidor_prof

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseRepository {

    private val database = FirebaseDatabase.getInstance().reference

    fun subirLectura(nivel: Int) {
        val timestamp = System.currentTimeMillis()
        database.child("lecturas").child(timestamp.toString()).setValue(nivel)
        database.child("estadoActual").setValue(nivel)
    }

    fun observarNivelActual(onCambio: (Int) -> Unit) {
        database.child("estadoActual").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nivel = snapshot.getValue(Int::class.java) ?: return
                onCambio(nivel)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }

    fun actualizarUmbralAlerta(umbral: Int) {
        database.child("umbralAlerta").setValue(umbral)
    }

    fun observarUmbralAlerta(onCambio: (Int) -> Unit) {
        database.child("umbralAlerta").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val umbral = snapshot.getValue(Int::class.java) ?: return
                onCambio(umbral)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}