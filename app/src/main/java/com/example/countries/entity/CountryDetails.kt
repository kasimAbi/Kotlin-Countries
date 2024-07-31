package com.example.countries.entity

import android.net.Uri

/**
 * A data class for the details of a country
 * @property wikiDataId The wikidata id of the country
 * @property flagImageUri The uri of the country's flag image
 * @author Kasim Mermer
 * @since 1.0.0
 */
data class CountryDetails (
    var wikiDataId: String,
    var flagImageUri: Uri
)