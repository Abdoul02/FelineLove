package com.abdoul.felinelove.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdoul.felinelove.databinding.FragmentHomeBinding
import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.FelineAdapter
import com.abdoul.felinelove.other.FelineLoadStateAdapter
import com.abdoul.felinelove.viewModel.FelineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val felineViewModel: FelineViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        setUpRecyclerView()
        return root
    }

    private fun setUpRecyclerView() {
        val felineAdapter = FelineAdapter()
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
                binding.felineRecyclerView.layoutManager = this
            }
        val footerAdapter = FelineLoadStateAdapter { felineAdapter.retry() }
        binding.felineRecyclerView.adapter = felineAdapter.withLoadStateFooter(
            footer = footerAdapter
        )
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == felineAdapter.itemCount && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        submitData(felineAdapter)
        setUpAdapter(felineAdapter)

    }

    private fun setUpAdapter(adapter: FelineAdapter) {
        adapter.setOnItemClickListener(object : FelineAdapter.OnItemClickListener {
            override fun onImageViewClick(position: Int, cardView: View) {
                val feline = adapter.getFeline(position)
                var localFeline: LocalFeline? = null
                feline?.let {
                    localFeline = LocalFeline(
                        it.id,
                        it.url,
                        it.breeds.firstOrNull()?.name,
                        it.breeds.firstOrNull()?.origin,
                        it.breeds.firstOrNull()?.description
                    )
                }
                localFeline?.let {
                    cardView.findNavController()
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToFelineDetailFragment(
                                it,
                                false
                            )
                        )
                }
            }
        })

        adapter.addLoadStateListener { loadState ->
            binding.networkProgress.isVisible = loadState.source.refresh is LoadState.Loading
            binding.errorLayout.isVisible = loadState.source.refresh is LoadState.Error
        }

        binding.btnRetry.setOnClickListener { adapter.retry() }
    }

    private fun submitData(adapter: FelineAdapter) {
        lifecycleScope.launchWhenStarted {
            felineViewModel.felines.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}