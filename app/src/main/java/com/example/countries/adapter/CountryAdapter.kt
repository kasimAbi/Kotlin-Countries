package com.example.countries.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.DetailActivity
import com.example.countries.R
import com.example.countries.databinding.TemplateListItemBinding
import com.example.countries.entity.Country
import com.google.android.material.imageview.ShapeableImageView

/**
 * Adapter for displaying a list of countries in a RecyclerView
 * @param countriesList The list of countries to display
 * @param onlySaved If true, only saved countries will be displayed
 * @author Kasim Mermer
 * @since 1.0.0
 */
class CountryAdapter(private val countriesList : MutableList<Country>, private val onlySaved: Boolean): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private var filteredCountriesList: List<Country> = if(onlySaved){
        countriesList.filter{
            it.saved
        }
    }else {
        countriesList
    }

    /**
     * ViewHolder for holding the view of each country item
     * @param binding The binding object for the country item layout
     */
    class CountryViewHolder(val binding: TemplateListItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Loads the XML-layout for each country item and creates a ViewHolder for it
     * @param parent The parent ViewGroup.
     * @param viewType The type of the view.
     * @return A new CountryViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = TemplateListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    /**
     * Toggles the saved state of a country and updates the icon
     * @param sivSaveCountry The ImageView for the save icon
     * @param currentCountry The country whose saved state is being toggled
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun toggleSaveCountry(sivSaveCountry: ShapeableImageView, currentCountry: Country) {
        if(!currentCountry.saved){
            sivSaveCountry.setImageResource(R.drawable.baseline_star_24)
            currentCountry.saved = true
        }else{
            sivSaveCountry.setImageResource(R.drawable.baseline_star_border_24)
            currentCountry.saved = false
        }

        filteredCountriesList = if(onlySaved){
            countriesList.filter{
                it.saved
            }
        }else{
            countriesList
        }
        /**
         * Notifies the data has been changed and any View reflecting the data should refresh itself
         */
        notifyDataSetChanged()
    }

    /**
     * Binds the data for each country-element to the ViewHolder
     * @param holder The ViewHolder for the country-element
     * @param position The position of the country-element in the list
     */
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = filteredCountriesList[position]
        holder.binding.apply {
            tvCountryName.text = currentCountry.name
            sivSaveCountry.setImageResource(
                if(currentCountry.saved) {
                    R.drawable.baseline_star_24
                }else{
                    R.drawable.baseline_star_border_24
                }
            )

            tvCountryName.setOnClickListener{
                val context = holder.itemView.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("code", currentCountry.code)
                context.startActivity(intent)
            }

            sivSaveCountry.setOnClickListener{
                toggleSaveCountry(sivSaveCountry, currentCountry)
            }
        }
    }

    /**
     * Returns the length of filteredCountriesList
     * @return Length of the filteredCountriesList
     */
    override fun getItemCount(): Int {
        return filteredCountriesList.size
    }
}
