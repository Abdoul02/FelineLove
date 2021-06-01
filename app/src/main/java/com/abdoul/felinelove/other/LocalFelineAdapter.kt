package com.abdoul.felinelove.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdoul.felinelove.databinding.FelineEntryBinding
import com.abdoul.felinelove.model.LocalFeline

class LocalFelineAdapter : RecyclerView.Adapter<LocalFelineAdapter.LocalFelineViewHolder>() {

    private lateinit var listener: OnItemClickListener
    private var data: ArrayList<LocalFeline> = ArrayList()

    inner class LocalFelineViewHolder(private val binding: FelineEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val felineCard = binding.cvFeline

        init {
            felineCard.setOnClickListener {
                listener.onImageViewClick(bindingAdapterPosition, it)
            }
        }

        fun bindFeline(item: LocalFeline) = with(binding) {
            imgCat.loadImage(item.url)
            categoryName.text = item.id
        }
    }

    interface OnItemClickListener {
        fun onImageViewClick(position: Int, cardView: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setData(locationData: List<LocalFeline>) {
        if (data.isNotEmpty()) data.clear()
        this.data.addAll(locationData)
        notifyDataSetChanged()
    }

    fun getLocalFeline(position: Int): LocalFeline {
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalFelineViewHolder {
        return LocalFelineViewHolder(
            FelineEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LocalFelineViewHolder, position: Int) {
        val item = data[position]
        holder.bindFeline(item)
    }

    override fun getItemCount(): Int {
        return if (!data.isNullOrEmpty()) data.size else 0
    }
}