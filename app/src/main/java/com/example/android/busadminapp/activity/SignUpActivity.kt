package com.example.android.busadminapp.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.android.busadminapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null

    private lateinit var auth: FirebaseAuth

    private lateinit var name : EditText
    private lateinit var phoneNo : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var registerBtn : Button
    private lateinit var loginBtn : TextView
    lateinit var back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Remove Action Bar
        supportActionBar?.hide()

        name = findViewById(R.id.name)
        phoneNo = findViewById(R.id.phoneNo)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        loginBtn  = findViewById(R.id.loginBtn)
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener{
            finish()
        }


        loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()

    }

    private  fun register(){
        registerBtn.setOnClickListener{
            var name:String = name.text.toString()
            var phone:String = phoneNo.text.toString()
            var email: String = email.text.toString()
            var password: String = password.text.toString()
            var confirmPassword: String = confirmPassword.text.toString()

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)  || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else if(password != confirmPassword){
                Toast.makeText(this,"Both password mismatch",Toast.LENGTH_LONG).show()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }else if(password.length<7){
                Toast.makeText(this, "Password must be atleast 6", Toast.LENGTH_SHORT).show()
            }else if(phone.length < 10){
                Toast.makeText(this, "Invalid Phone No.", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){

                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                        currentUserDb?.child("name")?.setValue(name)
                        currentUserDb?.child("phoneNo")?.setValue(phone)
                        currentUserDb?.child("email")?.setValue(email)

                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()

                        finish()
                    }else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }
    }
}