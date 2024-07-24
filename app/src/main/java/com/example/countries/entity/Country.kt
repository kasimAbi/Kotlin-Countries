package com.example.countries.entity

class Country(
    var code: String,
    var currencyCodes: String,
    var name: String,
    var wikiDataId: String,
    var saved: Boolean = false
)