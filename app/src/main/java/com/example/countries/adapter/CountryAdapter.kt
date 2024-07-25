package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.TemplateListItemBinding
import com.example.countries.entity.Country

class CountryAdapter(
    private val countries: MutableList<Country>
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: TemplateListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = TemplateListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    private fun toggleSaveCountry(ivSaveCountry: ImageView, currentCountry: Country){
        if(!currentCountry.saved){
            ivSaveCountry.setImageResource(R.drawable.saved_icon)
            currentCountry.saved = true
        }else{
            ivSaveCountry.setImageResource(R.drawable.unsaved_icon)
            currentCountry.saved = false
        }
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]
        holder.binding.apply {
            tvCountryName.text = currentCountry.name

            ivSaveCountry.setOnClickListener{
                toggleSaveCountry(ivSaveCountry, currentCountry)
            }

        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}
