package com.abdoul.felinelove.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdoul.felinelove.other.FELINE_TABLE
import kotlinx.android.parcel.Parcelize

@Entity(tableName = FELINE_TABLE)
@Parcelize
data class LocalFeline(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val url: String,
    val name: String?,
    val origin: String?,
    val description: String?
) : Parcelable
