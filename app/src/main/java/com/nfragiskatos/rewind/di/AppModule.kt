package com.nfragiskatos.rewind.di

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nfragiskatos.rewind.BuildConfig
import com.nfragiskatos.rewind.data.remote.TheMovieDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @SuppressLint("SimpleDateFormat")
    @Provides
    @Singleton
    fun providesTheMoveDbApi(): TheMovieDbApi {
        val builder = GsonBuilder()
            .registerTypeAdapter(
                Date::class.java,
                JsonDeserializer<Date> { json: JsonElement?, _, _ ->
                    val df = SimpleDateFormat("yyyy-MM-dd")
                    try {
                        return@JsonDeserializer json?.asString?.let { df.parse(it) }
                    } catch (ignored: Exception) {
                        return@JsonDeserializer null
                    }
                })
        val gson = builder.create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.THE_MOVIE_DB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }
}