package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private var tag = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


    }

    override fun onStart() {
        super.onStart()

        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        signup_button.setOnClickListener{


            val email: String = email_field2.text.toString()
            Log.d(tag, "onStart: $email")
            val pass: String = pass_field2.text.toString()
            Log.d(tag, "OnStart: $pass")


            if (email.isEmpty()) {
                email_field2.error = "Please enter your email"
            } else {
                email_field2.text.toString()
                if (pass.isEmpty()) {
                    pass_field2.error = "please set your password"
                } else {
                    pass_field2.text.toString()

                    //firebase create user
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            //successful
                            if (task.isSuccessful) {
                                //FIREBASE REGISTERED USER
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "you are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                //sign up not successfull
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }
        }



    }


}




