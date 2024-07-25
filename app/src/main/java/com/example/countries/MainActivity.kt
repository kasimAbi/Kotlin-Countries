package com.example.countries

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.countries.api.Countries
import com.example.countries.databinding.ActivityMainBinding
import com.example.countries.entity.Country

lateinit var countriesList: MutableList<Country>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val countries = Countries()

        countries.getCountries { c ->
            if (c != null) {
                countriesList = c
                replaceFragment(HomeFragment())
            } else {
                Log.d("SuccessmessageOnMainActivity", "Es hat fehlgeschlagen!")
            }
        }

        binding.bnvMainActivity.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeMenu -> replaceFragment(HomeFragment())
                R.id.savedMenu -> replaceFragment(SavedFragment())
                else ->{ }
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMainActivity, fragment)
        fragmentTransaction.commit()
    }
}