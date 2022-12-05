package com.example.myitemslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController


class ViewItems : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_items, container, false)
        //fetch data
        val fetchBtn = view.findViewById<Button>(R.id.viewItems)
        fetchBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_viewItems_to_fetchDataFragment)
        }

        // take user from view screen to add screen
        val button = view.findViewById<Button>(R.id.insertItem)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.action_viewItems_to_addItems)
        }

        val table = view.findViewById<TableLayout>(R.id.table_view)

        //call the viewItems function to get the items from the database
        val dbHelper = DatabaseClass(context)
        val cursor = dbHelper.viewItems()
        cursor!!.moveToFirst()

        while (cursor.isAfterLast == false) {

            val id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseClass.ID_COL))
            val item = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseClass.ITEM_COL))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseClass.DESCRIPTION_COL))
            val row = TableRow(context)
            row.setPadding(0, 10, 0, 10)

            //layout for the views in table
            val lparams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            val layoutPs = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 2f)
            val imageLP = TableRow.LayoutParams(0, 48, 1f)

            //creating the rows and it increments each time we add data
            val idText = TextView(context)
            idText.text = id
            idText.layoutParams = lparams
            row.addView(idText)
            val itemText = TextView(context)
            itemText.text = item
            itemText.layoutParams = layoutPs
            row.addView(itemText)
            val descriptionText = TextView(context)
            descriptionText.text = description
            descriptionText.layoutParams = lparams
            row.addView(descriptionText)

            val edit = ImageView(context)
            edit.setImageResource(R.drawable.ic_baseline_edit_24)
            edit.layoutParams = imageLP
            edit.setOnClickListener {
                val action = ViewItemsDirections.actionViewItemsToEditItems(id, item, description)
                view.findNavController().navigate(action)
            }
            row.addView(edit)
            val delete = ImageView(context)
            delete.setImageResource(R.drawable.ic_baseline_delete_24)
            delete.layoutParams = imageLP
            delete.setOnClickListener{

              //calling delete function fromm dbClass to delete an item by id
                dbHelper.deleteItem(id)

                view.findNavController().navigate(R.id.action_viewItems_self)
            }
            row.addView(delete)
            table.addView(row)
            cursor.moveToNext()
        }

        return view
    }

}