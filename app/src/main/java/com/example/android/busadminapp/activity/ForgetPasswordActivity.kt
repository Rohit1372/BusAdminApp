package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.busadminapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var resetPasswordBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.email_edt_text)
        resetPasswordBtn = findViewById(R.id.reset_pass_btn)

        resetPasswordBtn.setOnClickListener {
            var email: String = emailEt.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show()
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
        }

    }
}