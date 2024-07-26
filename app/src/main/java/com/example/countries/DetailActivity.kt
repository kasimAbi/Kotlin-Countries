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

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var country: Country
    private val countries = Countries()
    private var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        code = intent.getStringExtra("code").toString()
        country = countriesList.find{it.code == code}!!

        binding.tvCountryCode.setText("Country Code: $code")

        countries.getCountryDetails(code) { c ->
            if (c != null) {
                loadSvgImage(c.flagImageUri.toString())
                binding.bMoreInformation.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/${c.wikiDataId}"))
                    startActivity(intent)
                }
                try {
                    setSupportActionBar(binding.tbCountryDetails)
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                } catch (e: Exception) {
                    Log.d("Errormessage", e.toString())
                }
            } else {
                Log.d("SuccessmessageOnMainActivity", "Es hat fehlgeschlagen!")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.savedCountry -> {
                Log.d("test hier menu", "hallo")
                if(country.saved){
                    country.saved = false
                    countriesList.find{it.code == code}!!.saved = false
                    item.setIcon(R.drawable.baseline_star_border_36)
                }else{
                    country.saved = true
                    countriesList.find{it.code == code}!!.saved = true
                    item.setIcon(R.drawable.baseline_star_36)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadSvgImage(url: String){
        val imageLoader = Builder(this)
            .components{
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(this)
            .data(url)
            .target(binding.ivCountryFlag)
            .build()

        imageLoader.enqueue(request)
    }
}