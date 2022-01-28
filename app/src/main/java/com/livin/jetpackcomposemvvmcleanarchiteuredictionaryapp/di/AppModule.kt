package com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.BuildConfig
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Constants.BASE_URL
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.common.Constants.DB_NAME
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.Converters
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.local.DictionaryAppDatabase
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.remote.DictionaryAPI
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.repository.WordInfoRepositoryImpl
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.data.util.GsonConverter
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.repository.WordInfoRepository
import com.livin.jetpackcomposemvvmcleanarchiteuredictionaryapp.domain.usecase.GetWordDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordDetails {
        return GetWordDetails(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: DictionaryAppDatabase,
        api: DictionaryAPI
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.wordInfoDao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): DictionaryAppDatabase {
        return Room.databaseBuilder(
            app, DictionaryAppDatabase::class.java, DB_NAME
        ).addTypeConverter(Converters(GsonConverter(Gson())))
            .build()
    }

    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): DictionaryAPI = retrofit.create()

}