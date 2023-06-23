package com.biryulindevelop.unsplash.data.api

import com.biryulindevelop.unsplash.data.api.dto.digest.DigestListDto
import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoListDto
import retrofit2.http.*

interface ApiDigest {

    @GET("collections")
    suspend fun getDigests(@Query("page") page: Int): DigestListDto

    @GET("collections/{id}/photos")
    suspend fun getDigestPhotos(
        @Path("id") id: String,
        @Query("page") page: Int
    ): PhotoListDto

    @GET("collections/{id}")
    suspend fun getDigestInfo(
        @Path("id") id: String
    ): com.biryulindevelop.unsplash.data.api.dto.digest.DigestDto
}