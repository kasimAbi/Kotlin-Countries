package com.example.countries

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caverock.androidsvg.SVG
import com.example.countries.api.Countries
import com.example.countries.databinding.ActivityDetailBinding
import java.net.URL

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

        countries.getCountryDetails (code) { c ->
            if (c != null) {
                val thread = Thread {
                    try {
                        val flagURL = URL("https://upload.wikimedia.org/wikipedia/commons/1/19/Flag_of_Andorra.svg")
                        println("variable URL : $flagURL")
                        val svg = SVG.getFromInputStream(flagURL.openConnection().getInputStream())
                        val flagValue = svgToBitmap(svg, 100, 70)
                        println("variable in flagValue : $flagValue")
                        runOnUiThread {
                            binding.ivCountryFlag.setImageBitmap(flagValue)
                        }
                    } catch (e: Exception) {
                        Log.d("Error variable", e.toString())
                    }
                }
                thread.start()
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

    private fun svgToBitmap(svg: SVG, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        svg.setDocumentWidth(width.toFloat())
        svg.setDocumentHeight(height.toFloat())
        svg.renderToCanvas(canvas)
        return bitmap
    }

    /*
    private suspend fun getBitmap(url: String): Bitmap? {
        val imageLoader = ImageLoader(this)
        // Verwenden Sie eine alternative Bild-URL (z.B. PNG oder JPEG)
        val request = ImageRequest.Builder(this)
            .data("https://upload.wikimedia.org/wikipedia/commons/1/19/Flag_of_Andorra.svg")
            .build()
        val result = imageLoader.execute(request)
        return when (result) {
            is SuccessResult -> {
                val drawable = result.drawable
                if (drawable is BitmapDrawable) {
                    drawable.bitmap
                } else {
                    Log.e("getBitmap", "Drawable is not of type BitmapDrawable")
                    null
                }
            }
            is ErrorResult -> {
                Log.e("getBitmap", "Error loading image: ${result.throwable.message}")
                null
            }
            else -> {
                Log.e("getBitmap", "Unexpected result type")
                null
            }
        }
    }

     */
}