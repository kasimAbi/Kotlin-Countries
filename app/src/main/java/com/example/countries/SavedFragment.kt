package com.example.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private var fSavedBinding: FragmentSavedBinding? = null
    private val binding get() = fSavedBinding!!
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fSavedBinding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSavedCountrie.layoutManager = LinearLayoutManager(requireContext())
        countryAdapter = CountryAdapter(countriesList, true)
        binding.rvSavedCountrie.adapter = countryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fSavedBinding = null
    }
}