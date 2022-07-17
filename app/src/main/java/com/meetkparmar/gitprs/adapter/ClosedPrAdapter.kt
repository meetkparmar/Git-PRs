package com.meetkparmar.gitprs.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetkparmar.gitprs.databinding.ItemPrDetailsBinding
import com.meetkparmar.gitprs.models.PrDetails
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ClosedPrAdapter constructor(private val callback: Callback) : RecyclerView.Adapter<ClosedPrAdapter.ClosedPrViewHolder>() {

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
        holder.binding.goToWebsite.setOnClickListener {
            prDetails.html_url?.let { callback.openPRDetails(it) }
        }
        holder.binding.tvCreatedDate.text = "was created on ${formatedDate(prDetails.created_at)}"
        holder.binding.tvClosedDate.text = "and merged on ${formatedDate(prDetails.closed_at)}"
        holder.binding.tvPrNumber.text = "Pull Request #" + prDetails.number + " by"
        holder.binding.tvUser.text = prDetails.user.login
        Picasso.get()
            .load(prDetails.user.avatar_url)
            .transform(CropCircleTransformation())
            .into(holder.binding.ivImage);
    }

    override fun getItemCount(): Int {
        return prList.size
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatedDate(value: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: Date = dateFormat.parse(value)
        val formatter: DateFormat = SimpleDateFormat("dd MMM yyyy")
        return formatter.format(date)
    }

    class ClosedPrViewHolder(val binding: ItemPrDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}

interface Callback {
    fun openPRDetails(url: String)
}