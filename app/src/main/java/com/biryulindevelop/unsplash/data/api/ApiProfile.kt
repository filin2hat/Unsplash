package com.biryulindevelop.unsplash.data.api


import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoListDto
import com.biryulindevelop.unsplash.data.api.dto.profile.ProfileDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiProfile {

    @GET("me")
    suspend fun getProfile(): ProfileDto

    @GET("users/{username}/likes")
    suspend fun getProfileLikes(
        @Path("username") userName: String,
        @Query("page") page: Int
    ): PhotoListDto
}