package com.ksc91u.youtube.bean.api

import com.google.gson.GsonBuilder
import com.ksc91u.youtube.Constant
import com.ksc91u.youtube.dto.LiveDto
import com.ksc91u.youtube.dto.UserDto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@Configuration
class ApiBean {
    @Bean
    fun waveApi(@Autowired retrofit: Retrofit): WaveApiClient {
        return retrofit.create(WaveApiClient::class.java)
    }

    @Bean
    fun retrofit(): Retrofit {
        val gson = GsonBuilder().create()
        val gsonConverterFactory = GsonConverterFactory.create(gson)

        return Retrofit.Builder()
                .baseUrl(Constant.HOST)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }
}

interface WaveApiClient {
    @GET("${Constant.API_PREFIX}/lives/{liveId}")
    fun getLiveByLiveId(@Path("liveId") liveId: String): Call<LiveDto>

    @GET("${Constant.API_PREFIX}/lives/{liveId}/streamer")
    fun getStreamerByLiveId(@Path("liveId") liveId: String): Call<UserDto>

    @GET("${Constant.API_PREFIX}/users/{userId}")
    fun getUserById(@Path("userId") userId: String): Call<UserDto>

}