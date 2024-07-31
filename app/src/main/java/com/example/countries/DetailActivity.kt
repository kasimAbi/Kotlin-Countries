package com.example.countries

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.ImageLoader.Builder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.countries.api.Countries
import com.example.countries.databinding.ActivityDetailBinding
import com.example.countries.entity.Country
import com.example.countries.repository.CountryRepository.getCountries

/**
 * Activity to display the details of a selected country
 * @author Kasim Mermer
 * @since 1.0.0
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var country: Country
    private val countries = Countries()
    private var code = ""

    /**
     * Called when the activity is first created
     * Initializes the activity, sets up the view binding, and loads the country details
     * @param savedInstanceState If non-null, this activity is being re-constructed from a previous saved state as given here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        code = intent.getStringExtra("code").toString()
        country = getCountries().find{it.code == code}!!

        binding.tvCountryCode.setText("Country Code: $code")

        countries.getCountryDetails(code) { c ->
            if (c != null) {
                /**
                 * Set image for the flags
                 */
                loadSvgImage(c.flagImageUri.toString())
                /**
                 * Open browser on button click
                 */
                binding.bMoreInformation.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/${c.wikiDataId}"))
                    startActivity(intent)
                }
                /**
                 * Title of the action bar
                 */
                binding.tbCountryDetails.title = country.name
                try {
                    /**
                     * Set action bar
                     */
                    setSupportActionBar(binding.tbCountryDetails)
                    /**
                     * Set the back button on the top-left side
                     */
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                } catch (e: Exception) {
                    Log.d("Errormessage: setSupportActionBar", e.toString())
                }
            } else {
                Log.d("Errormessage: getCountryDetails", "Failed to get country details")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Initializes the contents of the activity's standard options menu
     * @param menu The options menu in which you place your items
     * @return true for the menu to be displayed / false to not show the menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        val savedCountryItem = menu?.findItem(R.id.savedCountry)
        if(country.saved){
            savedCountryItem?.setIcon(R.drawable.baseline_star_36)
        }else {
            savedCountryItem?.setIcon(R.drawable.baseline_star_border_36)
        }
        return true
    }

    /**
     * Called whenever an item in your options menu is selected
     * In this case it handles the star icon click to save or unsave the selected country
     * @param item The menu item that was selected
     * @return false to allow normal menu processing to proceed, true to consume it here
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.savedCountry -> {
                if(country.saved){
                    country.saved = false
                    getCountries().find{it.code == code}!!.saved = false
                    item.setIcon(R.drawable.baseline_star_border_36)
                }else{
                    country.saved = true
                    getCountries().find{it.code == code}!!.saved = true
                    item.setIcon(R.drawable.baseline_star_36)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Loads an svg image from the provided url and displays it in the imageview.
     * @param url The url of the svg image to load.
     */
    private fun loadSvgImage(url: String){
        /**
         * Creating an imageLoader which supports svg-images
         */
        val imageLoader = Builder(this)
            .components{
                add(SvgDecoder.Factory())
            }
            .build()

        /**
         * Get image from the url and put it to the imageview
         */
        val request = ImageRequest.Builder(this)
            .data(url)
            .target(binding.ivCountryFlag)
            .build()

        /**
         * Start image loading
         */
        imageLoader.enqueue(request)
    }
}