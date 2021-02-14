package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.kotlincountries.view.CountryFragmentArgs
import com.example.kotlincountries.R
import com.example.kotlincountries.databinding.FragmentCountryBinding
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid = 0
    private lateinit var dataBinding : FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData(view)


    }

    private fun observeLiveData(view: View){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country ->
            country?.let {

                dataBinding.selectedCountry = country

                /*
                view.findViewById<TextView>(R.id.countryName).text = country.countryName
                view.findViewById<TextView>(R.id.countryCapital).text = country.countryCapital
                view.findViewById<TextView>(R.id.countryCurrency).text = country.countryCurrency
                view.findViewById<TextView>(R.id.countryRegion).text = country.countryRegion
                view.findViewById<TextView>(R.id.countryLanguage).text = country.countryLanguage

                context?.let {
                    view.findViewById<ImageView>(R.id.countryImage).downloadFromUrl(country.imageUrl, CircularProgressDrawable(it))
                }

                 */

            }
        })
    }



}