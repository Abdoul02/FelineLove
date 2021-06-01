package com.abdoul.felinelove.network

import com.abdoul.felinelove.model.Feline
import com.abdoul.felinelove.other.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface FelineAPI {

    @GET("images/search?")
    suspend fun getCats(
        @Query("page") page: Int,
        @Query("has_breeds") value: Int? = 1,
        @Query("limit") size: Int? = 10,
        @Query("order") order: String? = "desc",
        @Query("api_key") api: String? = API_KEY
    ): List<Feline>
}