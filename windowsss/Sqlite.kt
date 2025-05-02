package com.example.windowsss

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Sqlite : AppCompatActivity() {
    private lateinit var db:DatabaseHelper
    private lateinit var e1: EditText
    private lateinit var e2: EditText
    private lateinit var e3: EditText
    private lateinit var t1: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sqlite)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DatabaseHelper(this)
        e1 = findViewById(R.id.et1)
        e2 = findViewById(R.id.et2)
        e3 = findViewById(R.id.et3)
        t1 = findViewById(R.id.tv)
        btn1 = findViewById(R.id.ab)
        btn2 = findViewById(R.id.ub)
        btn3 = findViewById(R.id.db)
        btn4 = findViewById(R.id.rb)
        btn1.setOnClickListener {
            insertdata()
        }
        btn2.setOnClickListener {
           updatedata()
        }
        btn3.setOnClickListener {
            deletedata()
        }
        btn4.setOnClickListener {
            getAllitems()
        }
    }
    private fun insertdata() {
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val quantity=e3.text.toString().toIntOrNull()
        if(name.isNullOrEmpty() || price==null|| quantity==null)
        {
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
            return
        }
        val ans=db.insertdata(name,price,quantity)
        if(ans!=-1L)
        {
            Toast.makeText(this,"Item added",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Item not added",Toast.LENGTH_SHORT).show()
        }
    }
    private fun updatedata(){
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val quantity=e3.text.toString().toIntOrNull()
        if(name.isNullOrEmpty() || price==null|| quantity==null)
        {
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
            return
        }
        val res=db.updatedata(name,price,quantity)
        if(res>0)
        {
            Toast.makeText(this,"Item updated",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Item not updated",Toast.LENGTH_SHORT).show()
        }
    }
    private fun deletedata(){
        val name=e1.text.toString()
        if(name.isNullOrEmpty() )
        {
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
            return
        }
        val ans=db.deletedata(name)
        if(ans>0)
        {
            Toast.makeText(this,"Item deleted",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Item not deleted",Toast.LENGTH_SHORT).show()
        }
    }
    private fun getAllitems(){
        val items=db.getAllitems()
        val name=e1.text.toString()
        val price=e2.text.toString().toDoubleOrNull()
        val quantity=e3.text.toString().toIntOrNull()
        val ss=StringBuilder()
        if(!name.isNullOrEmpty())
        {
            for(it in items)
            {
                ss.append("id:${it.id},name:${it.name},price:${it.price},quantity:${it.quantity}\n")
            }
        }
        else{
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
        }
        t1.text=ss.toString()
        t1.visibility= TextView.VISIBLE
    }

}