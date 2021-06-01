package com.abdoul.felinelove.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdoul.felinelove.databinding.FelineEntryBinding
import com.abdoul.felinelove.model.Feline

class FelineAdapter : PagingDataAdapter<Feline, FelineAdapter.FelineViewHolder>(FelineComparator) {

    private lateinit var listener: OnItemClickListener

    override fun onBindViewHolder(holder: FelineViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bindFeline(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FelineViewHolder {
        return FelineViewHolder(
            FelineEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class FelineViewHolder(private val binding: FelineEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val felineCard = binding.cvFeline

        init {
            felineCard.setOnClickListener {
                listener.onImageViewClick(bindingAdapterPosition,it)
            }
        }

        fun bindFeline(item: Feline) = with(binding) {
            imgCat.loadImage(item.url)
            categoryName.text = item.id
        }
    }

    fun getFeline(position: Int): Feline? {
        return getItem(position)
    }

    object FelineComparator : DiffUtil.ItemCallback<Feline>() {
        override fun areItemsTheSame(oldItem: Feline, newItem: Feline): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feline, newItem: Feline): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onImageViewClick(position: Int,cardView: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}