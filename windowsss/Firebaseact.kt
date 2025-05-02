package com.example.windowsss

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class Firebaseact : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var e1: EditText
    private lateinit var e2: EditText
    private lateinit var e3: EditText
    private lateinit var t1: TextView
    private lateinit var btn1:Button
    private lateinit var btn2:Button
    private lateinit var btn3:Button
    private lateinit var btn4:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_firebaseact)

        db= FirebaseFirestore.getInstance()
        e1=findViewById(R.id.et1)
        e2=findViewById(R.id.et2)
        e3=findViewById(R.id.et3)
        t1=findViewById(R.id.tv)
        btn1=findViewById(R.id.ab)
        btn2=findViewById(R.id.ub)
        btn3=findViewById(R.id.db)
        btn4=findViewById(R.id.rb)
        btn1.setOnClickListener {
            additems()
        }
        btn2.setOnClickListener {
            updateitems()
        }
        btn3.setOnClickListener {
            deleteitems()
        }
        btn4.setOnClickListener {
            readdata()
        }
    }
    private fun additems(){
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val qty=e3.text.toString().toDoubleOrNull()
        if(name.isNullOrEmpty() || price==null|| qty==null)
        {
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
        }
        val item = hashMapOf(
            "name" to name,
            "price" to price,
            "qty" to qty
        )
        db.collection("items")
            .document(name)
            .set(item)
            .addOnSuccessListener { documentReference ->
               Toast.makeText(this,"Item added",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Item not added",Toast.LENGTH_SHORT).show()
            }
    }
    private fun updateitems(){
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val qty=e3.text.toString().toDoubleOrNull()
        if(name.isNullOrEmpty() || price==null|| qty==null)
        {
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
        }
        val item=hashMapOf(
            "name" to name,
            "price" to price,
            "quantity" to qty
        )
        db.collection("items")
            .document(name)
            .update(item as Map<String, Any>)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this,"Item updated",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Item not updated",Toast.LENGTH_SHORT).show()
            }
    }
    private fun deleteitems(){
        val name=e1.text.toString()
        db.collection("items")
            .document(name)
            .delete()
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this,"Item deleted",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Item not deleted",Toast.LENGTH_SHORT).show()
            }
    }
    private fun readdata(){
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val qty=e3.text.toString().toDoubleOrNull()
        db.collection("items")
            .get()
            .addOnSuccessListener { result ->
                val ss=StringBuilder()
                for (document in result) {
                    val name=document.getString("name")
                    val price=document.getDouble("price")
                    val qty=document.getDouble("qty")
                    ss.append("Name: $name,price:$price,quantity:$qty\n\n")
                }
                t1.text=ss.toString()
                t1.visibility= View.VISIBLE
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Item not read",Toast.LENGTH_SHORT).show()
            }
        }
    }