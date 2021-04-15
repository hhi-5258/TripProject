package com.hhi.tripproject.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hhi.tripproject.model.data.TourList
import com.hhi.tripproject.view.main.MainRecyclerAdapter

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<Any>?) {
    items?.let {
        when (adapter) {
            is MainRecyclerAdapter ->
                (adapter as MainRecyclerAdapter).setTourList(it as ArrayList<TourList.TourData>)

        }
    }
}
