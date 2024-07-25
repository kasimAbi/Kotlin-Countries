package com.example.countries.entity

data class Country(
    var code: String,
    var currencyCodes: String,
    var name: String,
    var wikiDataId: String,
    var saved: Boolean = false
)