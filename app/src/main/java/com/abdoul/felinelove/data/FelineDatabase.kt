package com.abdoul.felinelove.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdoul.felinelove.model.LocalFeline

@Database(entities = [LocalFeline::class], version = 1)
abstract class FelineDatabase : RoomDatabase() {

    abstract fun felineDao(): FelineDao
}