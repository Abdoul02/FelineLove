package com.abdoul.felinelove.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdoul.felinelove.databinding.LoadingStateBinding

class FelineLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FelineLoadStateAdapter.FelineLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FelineLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = FelineLoadStateViewHolder(
        LoadingStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), retry
    )

    inner class FelineLoadStateViewHolder(
        private val binding: LoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }

            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            binding.textViewError.isVisible = loadState is LoadState.Error
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }
}