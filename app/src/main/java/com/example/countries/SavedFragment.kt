package com.example.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentSavedBinding
import com.example.countries.repository.CountryRepository.getCountries

/**
 * A Fragment representing the saved screen of the app, which displays a list of saved countries
 * @author Kasim Mermer
 * @since 1.0.0
 */
class SavedFragment : Fragment() {

    /**
     * Saved binding for the fragment views
     */
    private var fSavedBinding: FragmentSavedBinding? = null

    /**
     * Non-nullable and final value
     * get(): latest state of `fSavedBinding` will be returned every time `binding` is called
     * It reflecting any updates made to it
     */
    private val binding get() = fSavedBinding!!

    /**
     * Adapter for the RecyclerView that displays countries
     */
    private lateinit var countryAdapter: CountryAdapter

    /**
     * Creates the view controlled by the fragment
     * @param inflater LayoutInflater object can be used to access to the UI-elements in the XML-File
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return The View for the fragment's UI, or null
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fSavedBinding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored into the view
     * @param view The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is re-constructed from a previous saved state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * layoutManager decides how the view displays the list elements (vertically / horizontally)
         */
        binding.rvSavedCountries.layoutManager = LinearLayoutManager(requireContext())
        /**
         * Initializing the adapter and setting it to the recyclerview
         */
        countryAdapter = CountryAdapter(getCountries(), true)
        binding.rvSavedCountries.adapter = countryAdapter
    }

    /**
     * Called when the the fragment is being removed (switch to another fragment)
     */
    override fun onDestroyView() {
        super.onDestroyView()
        fSavedBinding = null
    }
}