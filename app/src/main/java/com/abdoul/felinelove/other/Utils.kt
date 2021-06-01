package com.abdoul.felinelove.other

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun TextView.shouldBeVisible(text: String?) {
    this.isVisible = !text.isNullOrEmpty()
}

fun showSnackBarMessage(root: View, message: String) {
    Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
}

const val API_KEY = "43a42c60-bb72-4ac4-9c0c-41d97a6bc90c"
const val NOT_AVAILABLE = "Not available"
const val FELINE_TABLE = "feline_info"
const val FELINE_DATABASE = "feline_db"