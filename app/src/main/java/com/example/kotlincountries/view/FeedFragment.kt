package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kotlincountries.R
import com.example.kotlincountries.adapter.CountryAdapter
import com.example.kotlincountries.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java) //hangi fragment hangi acti deyiz onu belirtiyoruz
        viewModel.refreshData() //view modeldeki refreh data metodu calisir.

        view.findViewById<RecyclerView>(R.id.countryList).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.countryList).adapter = countryAdapter



        /*
        view.findViewById<Button>(R.id.button).setOnClickListener {

            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)


        }

         */

        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).setOnRefreshListener {
            //kullanici refresh ettiginde yani ekrani asagi cektiginde.

            //country list i ve erroru gorunmez yapmaliyiz. data gelince kendi gorunur oluyor zaten
            view.findViewById<RecyclerView>(R.id.countryList).visibility = View.GONE
            view.findViewById<TextView>(R.id.countryError).visibility = View.GONE

            view.findViewById<ProgressBar>(R.id.countryLoading).visibility = View.VISIBLE //kendi progress barimizi gosterebiliriz
            viewModel.refreshFromAPI()
            view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false //kendi progressbarini kapatiyoruz. hem yukarda hem orta da gozukmesin diye.


        }

        observeLiveData(view)



    }

    private fun observeLiveData(view : View){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                //countries varsa diye boyle yaptik.
                view.findViewById<RecyclerView>(R.id.countryList).visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)

            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    //boolean true ise yani hata varsa.
                    view.findViewById<TextView>(R.id.countryError).visibility = View.VISIBLE
                }else{
                    view.findViewById<TextView>(R.id.countryError).visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it){
                    //eger true ise
                    view.findViewById<ProgressBar>(R.id.countryLoading).visibility = View.VISIBLE
                    view.findViewById<RecyclerView>(R.id.countryList).visibility = View.GONE
                    view.findViewById<TextView>(R.id.countryError).visibility = View.GONE
                }else{
                    view.findViewById<ProgressBar>(R.id.countryLoading).visibility = View.GONE
                }
            }
        })
    }

}