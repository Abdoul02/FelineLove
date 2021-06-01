package com.abdoul.felinelove.data

import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.FelineDataSource
import com.abdoul.felinelove.utils.BaseUnitTest
import com.abdoul.felinelove.viewModel.FelineViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class FelineViewModelShould : BaseUnitTest() {

    private val repository: FelineRepository = mock()
    private val felineDataSource: FelineDataSource = mock()
    private val localFeline: LocalFeline = mock()

    private val felineDataViewAction = FelineViewModel.ViewAction.FelineInfo(listOf(localFeline))

    @Test
    fun getFelineDataFromRepository() = runBlockingTest {
        val viewModel = mockLocalFelineData()

        viewModel.getAllFeline()

        verify(repository, times(1)).getAllFeline()
    }

    @Test
    fun insertFelineDataInRepository() = runBlockingTest {
        val viewModel = mockLocalFelineData()

        viewModel.insertFeline(localFeline)

        verify(repository, times(1)).insertFelineInfo(localFeline)
    }

    @Test
    fun deleteFelineDataFromRepository() = runBlockingTest {
        val viewModel = mockLocalFelineData()

        viewModel.deleteFeline(localFeline)

        verify(repository, times(1)).deleteFeline(localFeline)
    }

    @Test
    fun emitFelineDataFromRepositoryViaViewAction() = runBlockingTest {
        val viewModel = mockLocalFelineData()

        viewModel.getAllFeline()

        assertEquals(felineDataViewAction, viewModel.dataState.first())
    }

    private suspend fun mockLocalFelineData(): FelineViewModel {
        whenever(repository.getAllFeline()).thenReturn(
            flow {
                emit(listOf(localFeline))
            }
        )

        return FelineViewModel(repository, felineDataSource)
    }
}