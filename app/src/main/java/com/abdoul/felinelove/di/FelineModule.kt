package com.abdoul.felinelove.di

import android.app.Application
import androidx.room.Room
import com.abdoul.felinelove.data.FelineDao
import com.abdoul.felinelove.data.FelineDatabase
import com.abdoul.felinelove.network.FelineAPI
import com.abdoul.felinelove.other.FELINE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FelineModule {

    @Provides
    fun felineApi(retrofit: Retrofit): FelineAPI = retrofit.create(FelineAPI::class.java)

    @Provides
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun client(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): FelineDatabase {
        return Room.databaseBuilder(app, FelineDatabase::class.java, FELINE_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFelineDao(db: FelineDatabase): FelineDao = db.felineDao()
}