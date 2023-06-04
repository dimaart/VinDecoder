package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import network.ApiService
import network.Result
import network.myData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://vpic.nhtsa.dot.gov/"
class MainActivity : AppCompatActivity() {
    var vin = "1G1BC5SM7K7149506"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Decode button on click listener
        val decodeButton:Button = findViewById(R.id.button)
        decodeButton.setOnClickListener {
            val vinString: TextInputEditText = findViewById(R.id.textInput)
            vin = vinString.text.toString()
            getData()
       }

    }
    // Function that does api request
     fun getData(){
        val api: ApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
         GlobalScope.launch {
             val response : Response<myData> = api.getData(vin).awaitResponse() // Added suspend to getData() function
             if (response.isSuccessful){
                 val data: myData = response.body()!!
                 // Assigning response to my string builder
                 val myStringBuilder = StringBuffer()
                 myStringBuilder.append(data.Results.get(0).Make)
                 myStringBuilder.append("\n")
                 myStringBuilder.append(data.Results.get(0).Model)
                 myStringBuilder.append("\n")
                 myStringBuilder.append(data.Results.get(0).ModelYear)

                 Log.d("MY RESPONSE", myStringBuilder.toString())
                 // Adding vehicle information to the text view
                 val resultTextView: TextView = findViewById(R.id.textView)
                 resultTextView.text = myStringBuilder
                 Log.d("HERE", myStringBuilder.toString())

             }
         }

    }


}