package ca.gustavo.rgbeverything

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<EditText>(R.id.input_email)
        val passwordInput = findViewById<EditText>(R.id.input_password)
        val confirmPasswordInput = findViewById<EditText>(R.id.input_confirm_password)
        val createButton = findViewById<Button>(R.id.button_sign_in)

        createButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmedPassword = confirmPasswordInput.text.toString()

            if (password != confirmedPassword) {
                Toast.makeText(this, getString(R.string.password_mismatch_error), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Log.d("Gustavo", "createUserWithEmail:success")
                    } else {
                        val errorMsg = when (task.exception) {
                            is FirebaseAuthUserCollisionException -> "Account already exists!"
                            else -> "Something went wrong."
                        }

                        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
