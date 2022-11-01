package com.nurayyenilmez.countries.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nurayyenilmez.countries.R
import com.nurayyenilmez.countries.databinding.ItemCoutryRowBinding
import com.nurayyenilmez.countries.model.Country
import com.nurayyenilmez.countries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_coutry_row.view.*


  class CountryAdapter(val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener {


    class CountryViewHolder(var view: ItemCoutryRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
         val inflater=LayoutInflater.from(parent.context)
        val view =DataBindingUtil.inflate<ItemCoutryRowBinding>(inflater, R.layout.item_coutry_row,parent,false)
        return  CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    holder.view.country=countryList[position]
        holder.view.listener=this


    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v:View) {

        val uuid=v.CountryUuidText.text.toString().toInt()
        val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}

