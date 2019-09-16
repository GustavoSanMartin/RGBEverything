package ca.gustavo.rgbeverything

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.triggertrap.seekarc.SeekArc

class LightDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_details)

        val lightId = intent.getStringExtra(LIGHT_ID_TAG)

        val nameText = findViewById<TextView>(R.id.light_name)
        val seekBar = findViewById<SeekArc>(R.id.seekArc)
        val brightnessText = findViewById<TextView>(R.id.seekArcProgress)
        val colorText = findViewById<TextView>(R.id.color)

        val documentRef = Firebase.firestore.document("lights/$lightId")

        documentRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                seekBar.progress = documentSnapshot.getField<Int>("brightness") ?: 0
                nameText.text = documentSnapshot.getField<String>("name")
                val hexCode = documentSnapshot.getField<String>("color").formatHex()

                val color = Color.parseColor(hexCode)
                colorText.text = hexCode
                colorText.setTextColor(color)
                brightnessText.setTextColor(color)
                seekBar.arcColor = color
                seekBar.progressColor = color
            } else if (firebaseFirestoreException != null) {
                Log.d("Gustavo", "Something went wrong!", firebaseFirestoreException)
            }
        }

        colorText.setOnClickListener {
            ColorPickerDialog.Builder(this)
                .setTitle(R.string.choose_color_dialog_title)
                .setPositiveButton(R.string.pick, ColorEnvelopeListener { envelope, _ ->
                    documentRef.update("color", envelope.hexCode.substring(2))
                })
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(false)
                .show()
        }

        seekBar.setOnSeekArcChangeListener(object : SeekArc.OnSeekArcChangeListener {
            override fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean) {
                brightnessText.text = if (progress == 0) getString(R.string.light_off) else "$progress%"
            }

            override fun onStartTrackingTouch(seekArc: SeekArc?) {
            }

            override fun onStopTrackingTouch(seekArc: SeekArc?) {
                documentRef.update("brightness", seekArc?.progress ?: 0)
            }
        })
    }
}

fun String?.formatHex(): String {
    if (this == null) return "#FFFFFF" // TODO: error message
    return "#" + if (length > 6) this.substring(2) else this
}