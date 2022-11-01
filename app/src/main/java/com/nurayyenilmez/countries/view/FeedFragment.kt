package com.nurayyenilmez.countries.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurayyenilmez.countries.R
import com.nurayyenilmez.countries.adapter.CountryAdapter
import com.nurayyenilmez.countries.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {


    private lateinit var viewModel: FeedViewModel
    private val countryAdapter= CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feed,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

           viewModel=ViewModelProvider(this).get(FeedViewModel::class.java)
           viewModel.refreshData()

          countryList.layoutManager=LinearLayoutManager(context)
          countryList.adapter=countryAdapter




      swipeRefreshLayout.setOnRefreshListener {
           countryList.visibility=View.GONE
           countryError.visibility=View.GONE
            countryLoading.visibility=View.VISIBLE
            viewModel.refreshFromAPI()
        swipeRefreshLayout.isRefreshing=false
        }


        observerLiveData()
    }



    private fun observerLiveData(){
         viewModel.countries.observe(viewLifecycleOwner, Observer {countries->
             countries?.let{
                countryList.visibility=View.VISIBLE
                 countryAdapter.updateCountryList(countries)


             }

         })
        viewModel.countryError.observe(viewLifecycleOwner,Observer{error->
            error?.let {
               if (it) {
                countryError.visibility = View.VISIBLE
               }else{
                   countryError.visibility=View.GONE
               }
            }

        })
        viewModel.countryLoading.observe(viewLifecycleOwner,Observer{ loading->
            loading?.let {
                if (it){
                    countryLoading.visibility=View.VISIBLE
                    countryList.visibility=View.GONE
                    countryError.visibility=View.GONE
                }else{
                 countryLoading.visibility=View.GONE
                }
            }

        })

    }






}