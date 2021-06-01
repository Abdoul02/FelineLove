package com.abdoul.felinelove.data

import com.abdoul.felinelove.model.LocalFeline
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FelineRepository @Inject constructor(private val felineDao: FelineDao) : CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getAllFeline(): Flow<List<LocalFeline>> {
        return felineDao.getAllFeline()
    }

    fun insertFelineInfo(localFeline: LocalFeline) {
        launch {
            withContext(Dispatchers.IO) {
                felineDao.insert(localFeline)
            }
        }
    }

    fun deleteFeline(localFeline: LocalFeline) {
        launch {
            withContext(Dispatchers.IO) {
                felineDao.deleteFeline(localFeline)
            }
        }
    }
}