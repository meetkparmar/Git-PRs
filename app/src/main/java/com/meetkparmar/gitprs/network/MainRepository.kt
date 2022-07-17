package com.meetkparmar.gitprs.network

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getClosedPr() = retrofitService.getClosedPr()
}