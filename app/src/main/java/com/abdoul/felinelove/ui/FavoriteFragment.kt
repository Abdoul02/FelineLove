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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdoul.felinelove.databinding.FragmentFavoriteBinding
import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.LocalFelineAdapter
import com.abdoul.felinelove.viewModel.FelineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val felineViewModel: FelineViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    lateinit var localFelineAdapter: LocalFelineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root = binding.root
        setUpRecyclerView()
        getLocalFelines()
        return root
    }

    private fun getLocalFelines() {
        felineViewModel.getAllFeline()
        lifecycleScope.launchWhenStarted {
            felineViewModel.dataState.collect {
                when (it) {
                    is FelineViewModel.ViewAction.Loading -> showProgress()
                    is FelineViewModel.ViewAction.FelineInfo -> displayInfo(it.localFelines)
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        localFelineAdapter = LocalFelineAdapter()
        GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
            binding.felineRecyclerView.layoutManager = this
        }
        binding.felineRecyclerView.adapter = localFelineAdapter

        localFelineAdapter.setOnItemClickListener(object : LocalFelineAdapter.OnItemClickListener {
            override fun onImageViewClick(position: Int, cardView: View) {
                val localFeline = localFelineAdapter.getLocalFeline(position)
                cardView.findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToFelineDetailFragment(localFeline,true))
            }

        })
    }

    private fun displayInfo(localFelines: List<LocalFeline>) {
        binding.loading.isVisible = false
        localFelineAdapter.setData(localFelines)
    }

    private fun showProgress() {
        binding.loading.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}