package com.example.githubdemo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubdemo.R
import com.example.githubdemo.adapter.ClosedPrAdapter
import com.example.githubdemo.databinding.ActivityClosedPrBinding
import com.example.githubdemo.models.PrDetails
import com.example.githubdemo.network.MainRepository
import com.example.githubdemo.network.RetrofitService
import com.example.githubdemo.viewModel.ClosedPrViewModel

class ClosedPrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClosedPrBinding
    private lateinit var adapter: ClosedPrAdapter
    lateinit var viewModel: ClosedPrViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val repository = MainRepository(retrofitService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_closed_pr);
        initAdapter()
        initView()
    }

    private fun initAdapter() {
        adapter = ClosedPrAdapter()
        binding.recyclerview.adapter = adapter
    }

    private fun initView() {
        viewModel = ViewModelProvider(this)[ClosedPrViewModel::class.java]
        viewModel.init(repository)

        binding.progressBar.visibility = View.VISIBLE
        observeResponse()
        viewModel.getClosedPrs()
    }

    private fun observeResponse() {
        viewModel.prList.observe(this, Observer {
            updateUI(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            showErrorMessage(it)
        })
    }

    private fun updateUI(response: List<PrDetails>) {
        if (response.isEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.error.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.error.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
                adapter.setClosedPrList(response)
            }
    }

    private fun showErrorMessage(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
        binding.error.text = errorMessage
    }
}