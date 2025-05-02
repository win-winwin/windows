package com.example.windowsss

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.RouteListingPreference
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
    private val DATABASE_VERSION = 1
    private val DATABASE_NAME = "items"
    private val TABLE_NAME = "mytable"
        private val colid="id"
        private val colname="name"
        private val colprice="price"
        private var colqty="quantity"
}

    override fun onCreate(db: SQLiteDatabase?) {
        val que=("CREATE TABLE $TABLE_NAME("
                +"$colid INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"$colname TEXT UNIQUE,"
                +"$colprice REAL,"
                +"$colqty INTEGER)")
        if (db != null) {
            db.execSQL(que)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
        onCreate(db)
    }
    fun insertdata(name:String,price:Double,quantity:Int):Long{
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(colname,name)
        values.put(colprice,price)
        values.put(colqty,quantity)
        val id=db.insert(TABLE_NAME,null,values)
        return id
    }
    fun updatedata(name:String,price:Double,quantity: Int):Int{
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(colprice,price)
        values.put(colqty,quantity)
        val res=db.update(TABLE_NAME,values,"$colname=?", arrayOf(name))
        return res
    }
     fun deletedata(name:String):Int{
        val db=this.writableDatabase
        val values=ContentValues()
        val ans=db.delete(TABLE_NAME,"$colname=?", arrayOf(name))
        return ans
    }
    fun getAllitems():ArrayList<Item>{
        val allitems=ArrayList<Item>()
        val db=this.readableDatabase
        val query="select * from $TABLE_NAME"
        val cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst())
        {
            do{
                val item=Item(
                    cursor.getInt(cursor.getColumnIndexOrThrow(colid)),
                    cursor.getString(cursor.getColumnIndexOrThrow(colname)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(colprice)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(colqty))
                )
                allitems.add(item)
            }while(cursor.moveToNext())
        }
    return allitems
    }

}