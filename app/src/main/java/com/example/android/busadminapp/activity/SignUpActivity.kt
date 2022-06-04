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

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var registerBtn : Button
    private lateinit var loginBtn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        loginBtn  = findViewById(R.id.loginBtn)


        loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        register()

    }

    private  fun register(){
        registerBtn.setOnClickListener{
            var email: String = email.text.toString()
            var password: String = password.text.toString()
            var confirmPassword: String = confirmPassword.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)  || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else if(password != confirmPassword){
                Toast.makeText(this,"Both password mismatch", Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }
    }
}