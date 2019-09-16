package ca.gustavo.rgbeverything

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

const val LIGHT_ID_TAG = "LIGHT_ID_TAG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            val intent = Intent(this, AddLightActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.light_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val firebase = FirebaseAuth.getInstance()
        val user = firebase.currentUser

        Log.d("Gustavo main activity", user?.uid ?: "null")

        if (user != null) {
            val firestore = Firebase.firestore

            val collectionRef = firestore.collection("users/${user.uid}/associated_lights")

            collectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (querySnapshot != null) {
                    val lights = querySnapshot.toObjects<Light>()

                    recyclerView.adapter = LightListAdapter(lights) { lightId ->
                        val intent = Intent(this@MainActivity, LightDetailsActivity::class.java)
                        intent.putExtra(LIGHT_ID_TAG, lightId)
                        startActivity(intent)
                    }

                } else if (firebaseFirestoreException != null) {
                    Log.d("Gustavo", "user collection error", firebaseFirestoreException)
                }
            }
        }

    }
}