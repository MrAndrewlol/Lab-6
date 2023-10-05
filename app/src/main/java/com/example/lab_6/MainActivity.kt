package com.example.lab_6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    //lateinit var jarray: JSONArray
    var listaciudad : ArrayList<String> = ArrayList()
    lateinit var nomciudad : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView)
        var button = findViewById<Button>(R.id.button2)
        var spinner = findViewById<Spinner>(R.id.spinner3)
        listaciudad.add("Ciudad A Elegir")
        jsonparse()


        spinner!!.setOnItemSelectedListener(this)
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaciudad)

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)


// Configurar un OnItemSelectedListener
        // ...

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        button.setOnClickListener() {
            if (nomciudad != "Ciudad A Elegir"){
                ciudadactivity()
            }
            else
                Toast.makeText(getApplicationContext(), "No Se ha seleccionado una ciudad valida", Toast.LENGTH_SHORT).show()

        }


    }
    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        nomciudad = listaciudad[position]
    }

     override fun onNothingSelected(arg0: AdapterView<*>) {

    }



    fun jsonparse() {
        val urllink = "https://api.teleport.org/api/urban_areas/"
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)


        val request = StringRequest(
            Request.Method.GET,urllink,
            { response ->

                val data = response.toString()
                var jarray = JSONObject(data).getJSONObject("_links").getJSONArray("ua:item").getJSONObject(0).getString("name")
                val cantidad =JSONObject(data).getJSONObject("_links").getJSONArray("ua:item").length()
                var i = 0
                while (i < cantidad){

                    listaciudad.add(JSONObject(data).getJSONObject("_links").getJSONArray("ua:item").getJSONObject(i).getString("name"))
                    i++
                }
                Log.e("Objects",jarray.toString())


            },
            { })
        queue.add(request)

    }
    fun ciudadactivity(){
        val intent = Intent(this, Ciudad::class.java).also {
            it.putExtra("ciudad", nomciudad)
            startActivity(it)
        }

    }

}
