package com.example.countries.api

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class Countries {

    @OptIn(DelicateCoroutinesApi::class)
    fun getCountries(){

        GlobalScope.launch(Dispatchers.IO) {
            fetchCountries();
        }
    }

    suspend fun fetchCountries(){

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://wft-geo-db.p.rapidapi.com/v1/geo/countries?limit=10")
            .get()
            .addHeader("x-rapidapi-key", "9d5839f98emsh018008190d070b6p10b499jsn5fd7f82492be")
            .addHeader("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
            .build()

        try {
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val responseData = response.body?.string()
                withContext(Dispatchers.Main) {
                    Log.d("Successful", "Inhalt: ${responseData.toString()}")
                }
            }else{
                withContext(Dispatchers.Main){
                    Log.d("ResponseError", "Anfrage Fehlgeschlagen: ${response}")
                }
            }
        }catch (e: Exception){
            Log.d("Errormessage", "Fehlermeldung lautet: $e")
        }
    }
}