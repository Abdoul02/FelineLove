package com.abdoul.felinelove.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feline(
    val breeds: List<Breed?>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable

@Parcelize
data class Breed(
    val weight: Weight?,
    val id: String?,
    val name: String?,
    @SerializedName("vetstreet_url")
    val vetstreetUrl: String?,
    @SerializedName("vcahospitals_url")
    val vcahospitalsUrl: String?,
    val temperament: String?,
    val origin: String?,
    @SerializedName("country_codes")
    val countryCodes: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    val description: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    val indoor: Int?,
    @SerializedName("alt_names")
    val altNames: String?,
    val adaptability: Int?,
    @SerializedName("affection_level")
    val affectionLevel: Int?,
    @SerializedName("child_friendly")
    val childFriendly: Int?,
    @SerializedName("dog_friendly")
    val dogFriendly: Int?,
    @SerializedName("energy_level")
    val energyLevel: Int?,
    val grooming: Int?,
    @SerializedName("health_issues")
    val healthIssues: Int?,
    val intelligence: Int?,
    @SerializedName("shedding_level")
    val sheddingLevel: Int?,
    @SerializedName("social_needs")
    val socialNeeds: Int?,
    @SerializedName("stranger_friendly")
    val strangerFriendly: Int?,
    val vocalisation: Int?,
    val experimental: Int?,
    val hairless: Int?,
    val natural: Int?,
    val rare: Int?,
    val rex: Int?,
    @SerializedName("suppressed_tail")
    val suppressedTail: Int?,
    @SerializedName("short_legs")
    val shortLegs: Int?,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String?,
    val hypoallergenic: Int?,
    @SerializedName("reference_image_id")
    val referenceImageId: String?
) : Parcelable

@Parcelize
data class Weight(
    val imperial: String?,
    val metric: String?
) : Parcelable