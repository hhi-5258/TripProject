package com.hhi.tripproject.view.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hhi.tripproject.R
import com.hhi.tripproject.databinding.MainRecyclerItemBinding
import com.hhi.tripproject.model.data.TourList

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    private val tourList: ArrayList<TourList.TourData> = ArrayList()

    fun setTourList(list: ArrayList<TourList.TourData>){
        this.tourList.clear()
        this.tourList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tourList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tourList[position])
    }

    inner class ViewHolder(
        private val binding: MainRecyclerItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TourList.TourData){
            Glide.with(binding.root).load(data.tourimg)
                .error(R.drawable.ic_launcher_background)
                .into(binding.mainItemTourImage)

            binding.mainItemTourSpotName.text = data.tourspotname
            binding.mainItemTourTime.text = data.tourbegindate + " " + data.tourbegintime + " , " + data.tourhour + " 시간 동안 진행"
        }
    }
}