package com.biryulindevelop.unsplash.domain.usecase.implimentations

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.biryulindevelop.unsplash.data.repository.DigestPagingSource
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.DigestPagingUseCase
import javax.inject.Inject

class DigestPagingUseCaseImpl @Inject constructor(
    private val repository: DigestRemoteRepository
) : DigestPagingUseCase {
    override fun execute(): Pager<Int, Digest> {
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { DigestPagingSource(repository) }
        )
    }
}