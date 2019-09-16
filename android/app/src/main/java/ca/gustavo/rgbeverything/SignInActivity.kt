package ca.gustavo.rgbeverything

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

const val USER_UID_TAG = "USER_UID_TAG"

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<EditText>(R.id.input_email)
        val passwordInput = findViewById<EditText>(R.id.input_password)

        findViewById<Button>(R.id.sign_in_sign_in_button).setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(USER_UID_TAG, auth.currentUser?.uid)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext,
                            getString(R.string.error_login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        findViewById<Button>(R.id.button_create_account).setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}
