package com.abdoul.felinelove.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.abdoul.felinelove.R
import com.abdoul.felinelove.databinding.FragmentFelineDetailBinding
import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.NOT_AVAILABLE
import com.abdoul.felinelove.other.loadImage
import com.abdoul.felinelove.other.shouldBeVisible
import com.abdoul.felinelove.other.showSnackBarMessage
import com.abdoul.felinelove.viewModel.FelineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FelineDetailFragment : Fragment() {

    private val felineViewModel: FelineViewModel by viewModels()
    private var _binding: FragmentFelineDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFelineDetailBinding.inflate(inflater, container, false)
        val root = binding.root

        val felineDetails = FelineDetailFragmentArgs.fromBundle(requireArguments())
        val isSaved = felineDetails.isSaved
        if (isSaved) {
            binding.btnSave.setIconTintResource(R.color.red)
            binding.btnSave.text = getString(R.string.delete)
        }
        showDetails(felineDetails.localFeline)

        binding.btnShare.setOnClickListener {
            shareFelineInfo(felineDetails.localFeline)
        }

        binding.btnSave.setOnClickListener {
            if (isSaved)
                deleteFeline(felineDetails.localFeline)
            else
                saveFelineInfo(felineDetails.localFeline)
        }
        return root
    }

    private fun showDetails(feline: LocalFeline) {

        binding.imgCat.loadImage(feline.url)
        binding.categoryName.text = getString(R.string.name, feline.name ?: NOT_AVAILABLE)
        binding.categoryName.shouldBeVisible(feline.name)

        binding.origin.text = getString(R.string.origin, feline.origin ?: NOT_AVAILABLE)
        binding.origin.shouldBeVisible(feline.origin)

        binding.description.text =
            getString(R.string.description, feline.description ?: NOT_AVAILABLE)
        binding.description.shouldBeVisible(feline.description)

    }

    private fun shareFelineInfo(feline: LocalFeline) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        val infoToSend = StringBuilder()

        infoToSend.append("Please checkout this pet! \n")
        infoToSend.append("Image: ${feline.url} \n")
        feline.name?.let { name ->
            infoToSend.append("Name: $name \n")
        }
        feline.origin?.let { origin ->
            infoToSend.append("Origin: $origin \n")
        }

        feline.description?.let { description ->
            infoToSend.append("Description: $description")
        }

        intent.putExtra(Intent.EXTRA_TEXT, infoToSend.toString())
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun saveFelineInfo(feline: LocalFeline) {
        felineViewModel.insertFeline(feline)
        binding.btnSave.setIconTintResource(R.color.red)
        showSnackBarMessage(binding.btnSave, "Feline info saved!")
    }

    private fun deleteFeline(feline: LocalFeline) {
        felineViewModel.deleteFeline(feline)
        showSnackBarMessage(binding.btnSave, "Feline info deleted!")
        findNavController().navigate(FelineDetailFragmentDirections.actionFelineDetailFragmentToFavoriteFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}