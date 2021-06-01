package com.abdoul.felinelove.data

import androidx.room.*
import com.abdoul.felinelove.model.LocalFeline
import com.abdoul.felinelove.other.FELINE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface FelineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localFeline: LocalFeline)

    @Delete
    suspend fun deleteFeline(localFeline: LocalFeline)

    @Query("SELECT * FROM $FELINE_TABLE")
    fun getAllFeline(): Flow<List<LocalFeline>>
}