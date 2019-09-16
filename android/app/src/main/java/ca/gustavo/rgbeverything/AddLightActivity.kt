package ca.gustavo.rgbeverything

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddLightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_light)

        val database = Firebase.firestore
        val auth = FirebaseAuth.getInstance()

        val nameInput = findViewById<EditText>(R.id.light_name_input)
        val idInput = findViewById<EditText>(R.id.light_id_input)

        val submitBtn = findViewById<Button>(R.id.add_button)

        submitBtn.setOnClickListener {
            val id = idInput.text.toString()
            val name = nameInput.text.toString()

            if (id.isNotBlank() && name.isNotBlank()) {
                val light = hashMapOf("name" to name, "id" to id)

                database.collection("users/${auth.uid}/associated_lights").document(id).set(light)

                onBackPressed()
            } else {
                Toast.makeText(this, getString(R.string.add_light_invalid_input), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
