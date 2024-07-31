package com.example.countries.entity

/**
 * A data class for a country.
 * @property code The country code (for example "TR" for Türkiye)
 * @property currencyCodes The currency code of the country
 * @property name The name of the country
 * @property wikiDataId The wikidata id of the country
 * @property saved Shows whether the country is marked as saved by the user / default is false
 * @author Kasim Mermer
 * @since 1.0.0
 */
data class Country(
    var code: String,
    var currencyCodes: String,
    var name: String,
    var wikiDataId: String,
    var saved: Boolean = false
)