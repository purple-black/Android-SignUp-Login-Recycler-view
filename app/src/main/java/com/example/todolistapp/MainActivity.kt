package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listOfData: MutableList<Todos>
    private lateinit var adapter: RecyclerAdapter
    private lateinit var database: DatabaseReference
    private lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference.child("VALUE")


        val userId = intent.getStringExtra("user_id")
        val emailId =  intent.getStringExtra("email_id")

        user_email.text = "$emailId"
        user_id.text = "$userId"





        eventListener = object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfData.clear()
                adapter.notifyDataSetChanged()
                for (i in dataSnapshot.children) {
                    Log.d(TAG, "onDataChange: ${i.getValue(Todos::class.java)} $")
                    val model: Todos? = i.getValue(Todos::class.java)
                    //model.key = i.key
                    listOfData.add(model!!)
                    adapter.notifyItemInserted(adapter.itemCount - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        listOfData = mutableListOf()
        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = RecyclerAdapter(listOfData)
        recyclerView.adapter= adapter

        database.addValueEventListener(eventListener)



        log_out.setOnClickListener{
            //logging out of the app
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }

    override fun onStart() {
        super.onStart()
        addTodo!!.setOnClickListener {

            val newTodo: String = enterTodo!!.text.toString()

            if (newTodo.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "please enter Todo",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val todos = Todos(newtodo = newTodo)
                database.push().setValue(todos)


            }
        }


    }




}


