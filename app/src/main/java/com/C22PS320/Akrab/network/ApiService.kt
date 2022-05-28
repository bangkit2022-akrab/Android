package com.C22PS320.Akrab.network

import com.C22PS320.Akrab.network.response.AuthResponse
import com.C22PS320.Akrab.network.response.LevelResponse
import com.C22PS320.Akrab.network.response.ModuleQuizResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registUser(
        @Field("full_name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthResponse>

    @GET("level/{kelas}")
    fun getLevel(
        @Path("kelas") kelas: String,
        @Query("token") token: String
    ): Call<LevelResponse>

    @GET("modulQuiz/{level}")
    fun getModuleQuiz(
        @Path("level") level: String,
        @Query("token") token: String
    ): Call<ModuleQuizResponse>

}