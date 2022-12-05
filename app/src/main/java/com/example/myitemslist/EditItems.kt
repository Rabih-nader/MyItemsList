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

class EditItems : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_items, container, false)
        //inputs
        val insertItemName = view.findViewById<EditText>(R.id.editTxtItem)
        val insertDescription = view.findViewById<EditText>(R.id.EditTxtDescription)
        //Arguments
        val id = EditItemsArgs.fromBundle(requireArguments()).itemId
        val item = EditItemsArgs.fromBundle(requireArguments()).itemName
        val description = EditItemsArgs.fromBundle(requireArguments()).itemDescription

        insertItemName.setText(item)
        insertDescription.setText(description)

        val updateButton = view.findViewById<Button>(R.id.btnEditItem)
        updateButton.setOnClickListener {

            val edtItem = insertItemName.text.toString()
            val edtDescription = insertDescription.text.toString()

            //no empty inputs condition
            if(edtItem.isEmpty() || edtDescription.isEmpty()) {
                Toast.makeText(context, "Please insert your item", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //calling the function from dbclass to update data after editing it
            val dbHelper = DatabaseClass(context)
            dbHelper.editItems(id, edtItem, edtDescription)
            view.findNavController().navigate(R.id.action_editItems_to_viewItems)
        }
        return view
    }
}

