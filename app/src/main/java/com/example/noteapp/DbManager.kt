package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {
    val dbName = "MyNotes"
    val dbVersion =1
    //Table that data will stored on it
    val dbNotesTable ="Notes"

    // columns in table
    val colId ="ID"
    val colTitle = "Title"
    val colDes ="Description"

    //sql statement for create a table
    val sqlCreateTable ="CREATE TABLE IF NOT EXISTS "+dbNotesTable+" ("+colId+" INTEGER PRIMARY KEY,"+
            colTitle+" TEXT,"+colDes+" TEXT);"

    var sqlDb:SQLiteDatabase? = null

    constructor(context: AddNotesFragment){
        var dbHelper =DbHelperNotes(context)
        sqlDb= dbHelper.writableDatabase

    }

    inner class DbHelperNotes :SQLiteOpenHelper{

        var context:Context? = null
        constructor(context: Context):super (context,dbName,null,dbVersion){
            this.context=context
        }

        override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context,"DB created",Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            db!!.execSQL("Drop table IF EXISTS "+dbNotesTable)
        }
    }

    fun insertNote(values:ContentValues): Long {
       val id =sqlDb!!.insert(dbNotesTable,"",values)
        return id
    }
    
    fun query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sortOrder:String):Cursor{
        
        val db = SQLiteQueryBuilder()
        db.tables=dbNotesTable
        
        val cursor =db.query(sqlDb,projection,selection,selectionArgs,null,null,sortOrder)
        return cursor
    }

    fun delete(selection: String,selectionArgs: Array<String>):Int{

        val count = sqlDb!!.delete(dbNotesTable,selection,selectionArgs)
        return count
    }

    fun update(values: ContentValues,selection: String,selectionArgs: Array<String>):Int{

        val count = sqlDb!!.update(dbNotesTable,values,selection,selectionArgs)
        return count
    }
}