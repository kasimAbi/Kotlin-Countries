package com.example.countries.repository

import com.example.countries.entity.Country

object  CountryRepository {
    private var countriesList: MutableList<Country> = mutableListOf()

    fun setCountryList(countries: MutableList<Country>){
        countriesList.addAll(countries)
    }

    fun getCountries(): MutableList<Country> {
        return countriesList
    }
}