package com.example.countries.api

import android.util.Log
import androidx.core.net.toUri
import com.example.countries.entity.Country
import com.example.countries.entity.CountryDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Countries {
    private val scope = CoroutineScope(Dispatchers.Main)

    fun getCountries(callback: (MutableList<Country>?) -> Unit) {
        scope.launch {
            val countries = withContext(Dispatchers.IO) {
                fetchCountries()
            }
            callback(countries)
        }
    }

    fun getCountryDetails(code:String, callback: (CountryDetails?) -> Unit) {
        scope.launch {
            val country = withContext(Dispatchers.IO) {
                fetchCountryDetails(code)
            }
            callback(country)
        }
    }

    private suspend fun fetchCountries(): MutableList<Country>? {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://wft-geo-db.p.rapidapi.com/v1/geo/countries?limit=10")
            .get()
            .addHeader("x-rapidapi-key", "9d5839f98emsh018008190d070b6p10b499jsn5fd7f82492be")
            .addHeader("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val responseData = response.body?.string()
                val jsonObject = JSONObject(responseData);
                val dataArray = jsonObject.getJSONArray("data")
                val countries: MutableList<Country> = mutableListOf()

                for (index in 0 until dataArray.length()){
                    val countryObject = dataArray.getJSONObject(index)

                    val country: Country = Country(countryObject.getString("code"),
                        countryObject.getString("currencyCodes"),
                        countryObject.getString("name"),
                        countryObject.getString("wikiDataId"),
                        false)

                    countries.add(country)
                }
                countries
            }else{
                Log.d("Errormessage: response", response.toString())
                null
            }
        }catch (e: Exception){
            Log.d("Errormessage: fetchCountries", e.toString())
            null
        }
    }

    private suspend fun fetchCountryDetails(code: String): CountryDetails? {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://wft-geo-db.p.rapidapi.com/v1/geo/countries/$code")
            .get()
            .addHeader("x-rapidapi-key", "9d5839f98emsh018008190d070b6p10b499jsn5fd7f82492be")
            .addHeader("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                val responseData = response.body?.string()
                val jsonObject = JSONObject(responseData)
                val data = jsonObject.getJSONObject("data")
                val uriString: String = updateUri(data.getString("flagImageUri"))
                val countryDetails: CountryDetails = CountryDetails(data.getString("wikiDataId"), uriString.toUri())
                countryDetails
            }else{
                Log.d("Errormessage: response", response.toString())
                null
            }
        }catch (e: Exception){
            Log.d("Errormessage: fetchCountryDetails", e.toString())
            null
        }
    }

    private fun updateUri(url: String): String {
        return if (url.startsWith("http://")) {
            url.replaceFirst("http://", "https://")
        } else {
            url
        }
    }
}