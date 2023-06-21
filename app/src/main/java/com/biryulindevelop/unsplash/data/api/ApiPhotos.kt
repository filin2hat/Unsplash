package com.biryulindevelop.unsplash.data.api

import com.biryulindevelop.unsplash.data.api.photodto.PhotoDetailsDto
import com.biryulindevelop.unsplash.data.api.photodto.PhotoListDto
import com.biryulindevelop.unsplash.data.api.photodto.SearchDto
import com.biryulindevelop.unsplash.data.api.photodto.WrapperPhotoDto
import retrofit2.http.*

interface ApiPhotos {

    @GET("photos")
    suspend fun getPhotos(@Query("page") page: Int): PhotoListDto

    @GET("photos/{id}")
    suspend fun getPhotoDetails(@Path("id") id: String): PhotoDetailsDto

    @POST("photos/{id}/like")
    suspend fun like(@Path("id") id: String): WrapperPhotoDto

    @DELETE("photos/{id}/like")
    suspend fun unlike(@Path("id") id: String): WrapperPhotoDto

    @GET("search/photos")
    suspend fun searchPhotos(@Query("query") query: String, @Query("page") page: Int): SearchDto

}