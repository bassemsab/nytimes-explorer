package com.nytimes.explorer.articles.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.nytimes.explorer.articles.data.local.ArticlesDataBase
import com.nytimes.explorer.articles.data.local.Converters
import com.nytimes.explorer.articles.data.remote.NytApi
import com.nytimes.explorer.articles.data.repository.ArticlesRepository
import com.nytimes.explorer.core.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {


    @Provides
    @Singleton
    fun provideArticlesRepository(
        db: ArticlesDataBase,
        api: NytApi
    ): ArticlesRepository {
        return ArticlesRepository(api, db.dao)
    }


    @Provides
    @Singleton
    fun provideArticlesDataBase(app: Application): ArticlesDataBase {
        return Room.databaseBuilder(app, ArticlesDataBase::class.java, "articles_db").addTypeConverter(
            Converters(
                GsonParser(
                    Gson()
                )
            )
        ).build()
    }


    @Provides
    @Singleton
    fun provideNytApi(): NytApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(NytApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NytApi::class.java)

    }
}