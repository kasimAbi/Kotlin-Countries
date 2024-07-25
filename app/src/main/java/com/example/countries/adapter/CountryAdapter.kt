package com.example.countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.TemplateListItemBinding
import com.example.countries.entity.Country
import com.google.android.material.imageview.ShapeableImageView

class CountryAdapter(private val countriesList : MutableList<Country>, private val onlySaved: Boolean): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private var filteredCountriesList: List<Country> = if(onlySaved){
        countriesList.filter{
            it.saved
        }
    }else {
        countriesList
    }

    class CountryViewHolder(val binding: TemplateListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = TemplateListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

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
        notifyDataSetChanged()
    }

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
            sivSaveCountry.setOnClickListener{
                toggleSaveCountry(sivSaveCountry, currentCountry)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredCountriesList.size
    }
}
