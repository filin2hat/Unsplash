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

    @Test
    fun getDigestsWhenPageNumberIsValid() {
    }
    @Test
    fun getDigestsWhenPageNumberIsInvalidThenThrowException() {
    }

}