package com.example.countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.TemplateListItemBinding
import com.example.countries.entity.Country

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
    private fun toggleSaveCountry(ivSaveCountry: ImageView, currentCountry: Country) {
        if(!currentCountry.saved){
            ivSaveCountry.setImageResource(R.drawable.saved_icon)
            currentCountry.saved = true
        }else{
            ivSaveCountry.setImageResource(R.drawable.unsaved_icon)
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
            ivSaveCountry.setImageResource(
                if(currentCountry.saved) {
                    R.drawable.saved_icon
                }else{
                    R.drawable.unsaved_icon
                }
            )
            ivSaveCountry.setOnClickListener{
                toggleSaveCountry(ivSaveCountry, currentCountry)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredCountriesList.size
    }
}
