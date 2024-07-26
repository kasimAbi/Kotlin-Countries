package com.example.countries

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.ImageLoader.Builder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.countries.api.Countries
import com.example.countries.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        code = intent.getStringExtra("code").toString()

        binding.tvCountryCode.setText("Country Code: $code")

        val countries = Countries()

        countries.getCountryDetails(code) { c ->
            if (c != null) {
                loadSvgImage(c.flagImageUri.toString())
                binding.bMoreInformation.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/${c.wikiDataId}"))
                    startActivity(intent)
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