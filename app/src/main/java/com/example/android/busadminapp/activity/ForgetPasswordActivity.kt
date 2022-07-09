package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import com.example.android.busadminapp.R
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var resetPasswordBtn: Button
    lateinit var back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_edt_text)
        resetPasswordBtn = findViewById(R.id.reset_pass_btn)
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener{
            finish()
        }

        forgotPassword()

    }

    private fun forgotPassword(){
        resetPasswordBtn.setOnClickListener {

            val layout : ScrollView = findViewById(R.id.forgotPassword_layout)

            if(NetworkHelper.isNetworkConnected(this)){
                var email: String = emailEt.text.toString()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show()
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                } else {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(this, OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(this, "Unable to send reset mail", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                }

            }else{
                Snackbar.make(layout,"Sorry! There is no network connection.Please try later",
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}