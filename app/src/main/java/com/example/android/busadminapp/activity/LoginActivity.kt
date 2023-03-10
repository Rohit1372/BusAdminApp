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
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText
    private lateinit var loginRegisterBtn : TextView
    private lateinit var loginLoginBtn : Button
    private lateinit var resetPassword : TextView
    lateinit var back_arrow : ImageView

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
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener {
            onBackPressed()
        }

        loginRegisterBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            //finish()
        }

        resetPassword.setOnClickListener{
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        //Logged In
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        logIn()

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close this app?")
        builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
        })
        builder.create()
        builder.show()
    }

    private fun logIn(){
        loginLoginBtn.setOnClickListener {

            val layout : ScrollView = findViewById(R.id.login_layout)

            if(NetworkHelper.isNetworkConnected(this)){
                var email: String = loginEmail.text.toString()
                var password: String = loginPassword.text.toString()

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Snackbar.make(layout,"Please fill all the fields",
                        Snackbar.LENGTH_LONG).show()
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Snackbar.make(layout,"Invalid Email",
                        Snackbar.LENGTH_LONG).show()
                }else if(password.length<7){
                    Snackbar.make(layout,"Password must be atleast 6",
                        Snackbar.LENGTH_LONG).show()
                } else{
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                        if(task.isSuccessful) {
                            Snackbar.make(layout,"Successfully Logged In",
                                Snackbar.LENGTH_LONG).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Snackbar.make(layout,"Authentication failed",
                                Snackbar.LENGTH_LONG).show()
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