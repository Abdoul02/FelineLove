package com.abdoul.felinelove.data

import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.utils.BaseUnitTest
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
class FelineRepositoryShould : BaseUnitTest() {

    private val felineDao: FelineDao = mock()
    private val localFeline: LocalFeline = mock()


    @Test
    fun getFelineDataFromDb() = runBlockingTest {
        val repository = mockLocalFelineData()

        assertEquals(listOf(localFeline), repository.getAllFeline().first())
    }

    @Test
    fun shouldInsertFelineDataFromDB() = runBlockingTest {
        val repository = mockLocalFelineData()

        repository.insertFelineInfo(localFeline)

        verify(felineDao, times(1)).insert(localFeline)
    }

    private suspend fun mockLocalFelineData(): FelineRepository {
        whenever(felineDao.getAllFeline()).thenReturn(
            flow {
                emit(listOf(localFeline))
            }
        )

        return FelineRepository(felineDao)
    }
}