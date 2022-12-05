package com.example.myitemslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//Create Database name Items
class DatabaseClass(context: Context?) : SQLiteOpenHelper(context, "Items", null, DatabaseClass.DB_VERSION)
{ override fun onCreate(db: SQLiteDatabase) {
    //create table with 3 columns id,item name and description
    val query = ("CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, $ITEM_COL TEXT, $DESCRIPTION_COL TEXT)")
    db.execSQL(query)
}
    //function to add item
    fun addItem(item: String?, description: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_COL, item)
        values.put(DESCRIPTION_COL, description)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //function to select all items and show them in the table
    fun viewItems(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    //function to edit an item in the data base and Update it
    fun editItems(id: String, item: String, description: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_COL, item)
        values.put(DESCRIPTION_COL, description)
        db.update(TABLE_NAME, values, "$ID_COL=?", arrayOf(id))
        db.close()
    }

    //function to delete and item by id
    fun deleteItem(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id))
        db.close()
    }
//drop table if it is already exists
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_VERSION = 1
        const val TABLE_NAME = "ItemsTable"
        const val ID_COL = "itemId"
        const val ITEM_COL = "itemName"
        const val DESCRIPTION_COL = "description"
    }
}