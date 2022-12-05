package com.example.myitemslist

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header


class fetchDataFragment : Fragment() {
    lateinit var btnDisplay: Button
    lateinit var itemsDisplay: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fetch_data, container, false)
        val button = view.findViewById<Button>(R.id.btnBack)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.action_fetchDataFragment_to_viewItems)
        }
        btnDisplay = view.findViewById<Button>(R.id.btnDisplay)
        itemsDisplay = view.findViewById<TextView>(R.id.itemsDisplay)
        btnDisplay.setOnClickListener {
            getData()


        }
        return view
    }

    private fun getData() {
        val url = "https://jsonkeeper.com/b/PUS4"
        AsyncHttpClient().get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?) {
                val str = String(responseBody!!)
                itemsDisplay.text = str
            }

            override fun onFailure(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?, error: Throwable?) {
                itemsDisplay.text = "failed to connect to Api"
            }
        })

    }


}