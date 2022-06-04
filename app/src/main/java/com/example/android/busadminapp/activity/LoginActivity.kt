package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.busadminapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText
    private lateinit var loginRegisterBtn : TextView
    private lateinit var loginLoginBtn : Button
    private lateinit var resetPassword : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
        loginRegisterBtn = findViewById(R.id.login_register_btn)
        loginLoginBtn = findViewById(R.id.login_login_btn)
        resetPassword = findViewById(R.id.reset_password)

        loginRegisterBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetPassword.setOnClickListener{
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        //Logged In
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,Buses::class.java)
            startActivity(intent)
            finish()
        }

        logIn()

    }

    private fun logIn(){
        loginLoginBtn.setOnClickListener {
            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}