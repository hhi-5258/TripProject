package com.hhi.tripproject.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hhi.tripproject.R
import com.hhi.tripproject.databinding.MainRecyclerItemBinding
import com.hhi.tripproject.model.data.TourList

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    private val tourList = mutableListOf<TourList.TourData>()

    fun setTourList(list: ArrayList<TourList.TourData>) {
        tourList.clear()
        tourList.addAll(list)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclerAdapter.ViewHolder {
        val binding =
            MainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bind(data: TourList.TourData) {
            Glide.with(binding.root).load(data.tourimg)
                .error(R.drawable.ic_launcher_background)
                .transform(RoundedCorners(5))
                .into(binding.mainItemTourImage)

            binding.mainItemTourSpotName.text = data.tourspotname
            binding.mainItemTourTime.text = "${data.tourbegindate} ${data.tourbegintime} 출발, 투어 시간 : ${data.tourhour} 시간"
        }
    }
}