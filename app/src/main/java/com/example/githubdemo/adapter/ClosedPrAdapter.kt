package com.example.githubdemo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubdemo.databinding.ItemPrDetailsBinding
import com.example.githubdemo.models.PrDetails
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ClosedPrAdapter : RecyclerView.Adapter<ClosedPrAdapter.ClosedPrViewHolder>() {

    private var prList = mutableListOf<PrDetails>()

    fun setClosedPrList(list: List<PrDetails>) {
        this.prList.clear()
        this.prList.addAll(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ClosedPrViewHolder {
        val binding = ItemPrDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ClosedPrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClosedPrViewHolder, position: Int) {
        val prDetails = prList[position]
        holder.binding.tvTitle.text = prDetails.title
        holder.binding.tvCreatedDate.text = formatedDate(prDetails.created_at)
        holder.binding.tvClosedDate.text = formatedDate(prDetails.closed_at)
        holder.binding.tvUser.text = prDetails.user.login
        Picasso.get().load(prDetails.user.avatar_url).into(holder.binding.ivImage);
    }

    override fun getItemCount(): Int {
        return prList.size
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatedDate(value: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: Date = dateFormat.parse(value)
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }

    class ClosedPrViewHolder(val binding: ItemPrDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}