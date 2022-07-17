package com.example.githubdemo.network

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getClosedPr() = retrofitService.getClosedPr()
}