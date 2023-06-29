package com.biryulindevelop.data.api

import com.biryulindevelop.domain.dto.photo.PhotoListDto
import retrofit2.http.*

interface ApiDigest {

    @GET("collections")
    suspend fun getDigests(@Query("page") page: Int): com.biryulindevelop.domain.dto.digest.DigestListDto

    @GET("collections/{id}/photos")
    suspend fun getDigestPhotos(
        @Path("id") id: String,
        @Query("page") page: Int
    ): PhotoListDto

    @GET("collections/{id}")
    suspend fun getDigestInfo(
        @Path("id") id: String
    ): com.biryulindevelop.domain.dto.digest.DigestDto
}