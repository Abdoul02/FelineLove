package com.abdoul.felinelove.other

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abdoul.felinelove.model.Feline
import com.abdoul.felinelove.network.FelineAPI
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FelineDataSource @Inject constructor(private val api: FelineAPI) :
    PagingSource<Int, Feline>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feline> {

        return try {
            val nextPageNumber = params.key ?: 0
            val response = api.getCats(nextPageNumber)
            val nextKey = if (response.isNotEmpty()) nextPageNumber + 1 else null
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Feline>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}