package com.hhi.tripproject.model.data

class TourList {
    data class Request(
        val useridx: Int
    )

    data class Response(
        val success: Boolean,
        val message: String,
        val data: Data
    )

    data class Data(
        val useridx: Int,
        val tourlist: ArrayList<TourList.TourData>
    )

    data class TourData(
        val tourimg: String,
        val tourspotname: String,
        val tourbegindate: String,
        val tourbegintime: String,
        val tourhour: Int
    )
}