package com.biryulindevelop.data.repository

import com.biryulindevelop.data.api.ApiDigest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Test
import org.junit.jupiter.api.BeforeEach

class DigestRemoteRepositoryImplTest {

    @MockK
    private lateinit var apiDigest: ApiDigest

    private lateinit var digestRemoteRepository: DigestRemoteRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        this.digestRemoteRepository = DigestRemoteRepositoryImpl(this.apiDigest)
    }


    /**Should return a list of digests when a valid page number is provided*/
    @Test
    fun getDigestsWhenPageNumberIsValid() {
    }

    /**Should throw an exception when an invalid page number is provided*/
    @Test
    fun getDigestsWhenPageNumberIsInvalidThenThrowException() {
    }

}