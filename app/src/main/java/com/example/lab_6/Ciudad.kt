package com.example.lab_6

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class Ciudad : AppCompatActivity() {

    lateinit var modciudad: String
    lateinit var jarray : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad)

        val ciudad = intent.getStringExtra("ciudad")
        val textView = findViewById<TextView>(R.id.textView2)






        textView.text = "Ciudad: \n" + ciudad.toString()
        modciudad = ciudad.toString()
        jsonparse()


    }



    fun jsonparse() {
        val urllink = "https://api.teleport.org/api/urban_areas/slug:"+ modciudad.lowercase()  + "/images"
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)


        val request = StringRequest(
            Request.Method.GET,urllink,
            { response ->
                val executor = Executors.newSingleThreadExecutor()
                val imageView = findViewById<ImageView>(R.id.imageView)
                val handler = Handler(Looper.getMainLooper())
                val data = response.toString()
                var jarray = JSONObject(data).getJSONArray("photos").getJSONObject(0).getJSONObject("image").get("mobile").toString()

                Log.e("Objects",jarray)
                var image: Bitmap?
                executor.execute{
                    try {
                        val `in` = URL(jarray).openStream()
                        image = BitmapFactory.decodeStream(`in`)

                        // Only for making changes in UI
                        handler.post {
                            imageView.setImageBitmap(image)
                        }
                    }

                    // If the URL doesnot point to
                    // image or any other kind of failure
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            },
            { })
        queue.add(request)

    }
}