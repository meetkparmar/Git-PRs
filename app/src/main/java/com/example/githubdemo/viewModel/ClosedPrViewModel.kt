package com.example.githubdemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubdemo.models.PrDetails
import com.example.githubdemo.network.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClosedPrViewModel: ViewModel() {

    val prList = MutableLiveData<List<PrDetails>>()
    val errorMessage = MutableLiveData<String>()
    private lateinit var repository: MainRepository

    fun init(repository: MainRepository) {
        this.repository = repository
    }

    fun getClosedPrs() {
        val response = repository.getClosedPr()
        response.enqueue(object : Callback<List<PrDetails>> {
            override fun onResponse(call: Call<List<PrDetails>>, response: Response<List<PrDetails>>) {
                prList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<PrDetails>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
