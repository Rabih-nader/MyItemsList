package com.example.myitemslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController



class AddItems : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_items, container, false)

        val addButton = view.findViewById<Button>(R.id.btnInsertItem)
        addButton.setOnClickListener {
            val itemName = view.findViewById<EditText>(R.id.txtItem).text.toString()
            val description = view.findViewById<EditText>(R.id.txtDescription).text.toString()

            if(itemName.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Please insert item", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val dbHelper = DatabaseClass(context)

            //calling function addItems
            dbHelper.addItem(itemName, description)            //no empty inputs condition

            //go from add items screen to view items
            view.findNavController().navigate(R.id.action_addItems_to_viewItems)
        }

        return view
    }
}