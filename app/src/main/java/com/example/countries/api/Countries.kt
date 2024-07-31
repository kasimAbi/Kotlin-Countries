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

/**
 * A class to handle API calls related to countries
 * @author Kasim Mermer
 * @since 1.0.0
 */
class Countries {

    /**
     * API-Key and host
     */
    private val apiKey: String = "9d5839f98emsh018008190d070b6p10b499jsn5fd7f82492be"
    private val host: String = "wft-geo-db.p.rapidapi.com"

    private val scope = CoroutineScope(Dispatchers.Main)

    /**
     * Returns a list of countries from the API and send it to the callback function
     * @param callback The function to call with the list of countries or null if an error occurs
     */
    fun getCountries(callback: (MutableList<Country>?) -> Unit) {
        scope.launch {
            val countries = withContext(Dispatchers.IO) {
                fetchCountries()
            }
            callback(countries)
        }
    }

    /**
     * Returns the details of a single country by its code and send it to the callback function
     * @param code The country code
     * @param callback The function to call with the country details or null if an error occurs
     */
    fun getCountryDetails(code:String, callback: (CountryDetails?) -> Unit) {
        scope.launch {
            val country = withContext(Dispatchers.IO) {
                fetchCountryDetails(code)
            }
            callback(country)
        }
    }

    /**
     * Fetches the list of countries from the API
     * The function can be used in a coroutine
     * @return A mutable list of Country objects or null if an error occurs
     */
    private suspend fun fetchCountries(): MutableList<Country>? {
        /**
         * Is created for http-requests
         */
        val client = OkHttpClient()

        /**
         * Use builder-object for creating the request
         * Setting the url for the request with limit 10
         * Declaring that it's a "GET"-request
         * Add api-key to the request
         * Add api-host to the request
         * Creates the request-object
         */
        val request = Request.Builder()
            .url("https://wft-geo-db.p.rapidapi.com/v1/geo/countries?limit=10")
            .get()
            .addHeader("x-rapidapi-key", apiKey)
            .addHeader("x-rapidapi-host", host)
            .build()

        return try {
            /**
             * Executes the request and gets the response and checks if the request was successful
             */
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                /**
                 * Gets the response body as a string
                 * Converts the response string to a JSONObject
                 * Gets the JSON array containing the countries
                 * Creates an empty list of countries
                 */
                val responseData = response.body?.string()
                val jsonObject = JSONObject(responseData.toString());
                val dataArray = jsonObject.getJSONArray("data")
                val countries: MutableList<Country> = mutableListOf()

                /**
                 * Iterates over the JSON array
                 * Gets the JSON object for each country
                 * Creates a Country object with the data from the JSON object
                 * Adds the Country object to the list of countries
                 */
                for (index in 0 until dataArray.length()){
                    val countryObject = dataArray.getJSONObject(index)

                    val country: Country = Country(countryObject.getString("code"),
                        countryObject.getString("currencyCodes"),
                        countryObject.getString("name"),
                        countryObject.getString("wikiDataId"),
                        false)

                    countries.add(country)
                }
                /**
                 * Returns the list of countries
                 */
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

    /**
     * Fetches the details of a single country by its code from the API
     * The function can be used in a coroutine
     * @param code The country code
     * @return A CountryDetails object or null if an error occurs
     */
    private suspend fun fetchCountryDetails(code: String): CountryDetails? {
        /**
         * Is created for http-requests
         */
        val client = OkHttpClient()

        /**
         * Use builder-object for creating the request
         * Setting the url for the request
         * Declaring that it's a "GET"-request
         * Add api-key to the request
         * Add api-host to the request
         * Creates the request-object
         */
        val request = Request.Builder()
            .url("https://wft-geo-db.p.rapidapi.com/v1/geo/countries/$code")
            .get()
            .addHeader("x-rapidapi-key", apiKey)
            .addHeader("x-rapidapi-host", host)
            .build()

        return try {
            /**
             * Executes the request and gets the response and checks if the request was successful
             */
            val response = client.newCall(request).execute()
            if(response.isSuccessful){
                /**
                 * Gets the response body as a string
                 * Converts the response string to a JSONObject
                 * Gets the JSON object containing the country.
                 * Gets the flag image uri from the data-object and converts http to https
                 * Create and return a CountryDetails-object
                 */
                val responseData = response.body?.string()
                val jsonObject = JSONObject(responseData.toString())
                val data = jsonObject.getJSONObject("data")
                val uriString: String = updateUri(data.getString("flagImageUri"))
                return CountryDetails(data.getString("wikiDataId"), uriString.toUri())
            }else{
                Log.d("Errormessage: response", response.toString())
                null
            }
        }catch (e: Exception){
            Log.d("Errormessage: fetchCountryDetails", e.toString())
            null
        }
    }

    /**
     * Updates the uri to use https if it starts with http
     * @param url The original url
     * @return The updated url
     */
    private fun updateUri(url: String): String {
        return if (url.startsWith("http://")) {
            url.replaceFirst("http://", "https://")
        } else {
            url
        }
    }
}