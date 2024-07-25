package com.example.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var fHomeBinding: FragmentHomeBinding? = null
    private val binding get() = fHomeBinding!!
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())
        countryAdapter = CountryAdapter(countriesList, false)
        binding.rvCountries.adapter = countryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fHomeBinding = null
    }
}