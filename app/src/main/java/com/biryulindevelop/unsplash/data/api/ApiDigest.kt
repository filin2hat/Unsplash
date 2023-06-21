package com.biryulindevelop.unsplash.data.api

import com.biryulindevelop.unsplash.data.api.digestdto.DigestDto
import com.biryulindevelop.unsplash.data.api.digestdto.DigestListDto
import com.biryulindevelop.unsplash.data.api.photodto.PhotoListDto
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
    ): DigestDto
}