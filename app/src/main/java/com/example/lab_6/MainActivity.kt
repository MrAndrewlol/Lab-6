package com.example.lab_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView)
        var button = findViewById<Button>(R.id.button)
        val spinner: Spinner = findViewById(R.id.spinner2)
        // ...

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        button.setOnClickListener() {
            jsonparse()


        }


    }



    fun jsonparse() {
        val urllink = "https://api.teleport.org/api/urban_areas/"
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)


        val request = StringRequest(
            Request.Method.GET,urllink,
            { response ->

                val data = response.toString()
                val jarray = JSONObject(data).getJSONObject("_links").getJSONArray("ua:item")
                Log.e("Objects",jarray.toString())


            },
            { })
        queue.add(request)

    }

}
