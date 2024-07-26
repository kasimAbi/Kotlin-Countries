package com.example.countries.repository

import com.example.countries.entity.Country

object  CountryRepository {
    private var countriesList: MutableList<Country> = mutableListOf()

    fun setCountryList(countries: MutableList<Country>){
        countriesList = countries
    }

    fun addCountry(country: Country) {
        countriesList.add(country)
    }

    fun updateCountry(code: String, saved: Boolean) {
        val country = countriesList.find { it.code == code }
        country?.saved = saved
    }

    fun getCountries(): MutableList<Country> {
        return countriesList
    }
}