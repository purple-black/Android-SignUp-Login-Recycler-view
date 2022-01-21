package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.FirebaseAuth.*

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }

    override fun onStart() {
        super.onStart()
        Signup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        button.setOnClickListener {


            val loginemail: String = email_field.text.toString()
            Log.d(TAG, "onStart: $loginemail")
            val loginpass: String = pass_field.text.toString()
            Log.d(TAG, "OnStart: $loginpass")


            if (loginemail.isEmpty()) {
                email_field.error = "Please enter your email"
            } else {
                email_field.text.toString()
                if (loginpass.isEmpty()) {
                    pass_field.error = "please enter your password"
                } else {
                    pass_field.text.toString()

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(loginemail, loginpass)
                        .addOnCompleteListener { task ->
                            //successful
                            if (task.isSuccessful) {


                                Toast.makeText(
                                    this@LoginActivity,
                                    "you are logged in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", loginemail)
                                startActivity(intent)
                                finish()
                            } else {

                                Toast.makeText(
                                    this@LoginActivity,
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