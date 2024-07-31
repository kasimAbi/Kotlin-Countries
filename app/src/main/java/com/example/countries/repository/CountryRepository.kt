package com.example.countries.repository

import com.example.countries.entity.Country

/**
 * A singleton repository for managing the list of countries
 * @author Kasim Mermer
 * @since 1.0.0
 */
object  CountryRepository {
    private var countriesList: MutableList<Country> = mutableListOf()

    /**
     * Sets the country list by adding all the provided countries to the current list
     * @param countries The list of countries to be added to the repository
     */
    fun setCountryList(countries: MutableList<Country>){
        countriesList.addAll(countries)
    }

    /**
     * Returns the list of countries from the repository
     * @return The current list of countries
     */
    fun getCountries(): MutableList<Country> {
        return countriesList
    }
}