package com.meetkparmar.gitprs.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.meetkparmar.gitprs.R
import com.meetkparmar.gitprs.databinding.ActivityClosedPrBinding
import com.meetkparmar.gitprs.adapter.Callback
import com.meetkparmar.gitprs.adapter.ClosedPrAdapter
import com.meetkparmar.gitprs.models.PrDetails
import com.meetkparmar.gitprs.network.MainRepository
import com.meetkparmar.gitprs.network.RetrofitService
import com.meetkparmar.gitprs.viewModel.ClosedPrViewModel

class ClosedPrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClosedPrBinding
    private lateinit var adapter: ClosedPrAdapter
    lateinit var viewModel: ClosedPrViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val repository = MainRepository(retrofitService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_closed_pr);
        initAdapter()
        initView()
    }

    private fun initAdapter() {
        adapter = ClosedPrAdapter(object : Callback {
            override fun openPRDetails(url: String) {
                startActivity(Intent(Intent.ACTION_VIEW , Uri.parse(url)))
            }
        })
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