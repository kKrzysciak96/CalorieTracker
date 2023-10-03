package com.plcoding.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.plcoding.tracker_data.local.entity.TrackerDataBase
import com.plcoding.tracker_data.remote.OpenFoodApi
import com.plcoding.tracker_data.repository.TrackerRepositoryImpl
import com.plcoding.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(okHttpClient: OkHttpClient): OpenFoodApi {
        return Retrofit
            .Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDataBase(app: Application): TrackerDataBase {
        return Room.databaseBuilder(
            context = app,
            klass = TrackerDataBase::class.java,
            name = "tracker_data_base"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(db: TrackerDataBase, api: OpenFoodApi): TrackerRepository {
        return TrackerRepositoryImpl(dao = db.provideDao(), api = api)
    }
}