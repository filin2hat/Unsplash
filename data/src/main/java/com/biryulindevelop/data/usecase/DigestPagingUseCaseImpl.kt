package com.biryulindevelop.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.biryulindevelop.data.repository.DigestPagingSource
import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import com.biryulindevelop.domain.usecase.DigestPagingUseCase
import javax.inject.Inject

class DigestPagingUseCaseImpl @Inject constructor(
    private val repository: DigestRemoteRepository
) : DigestPagingUseCase {
    override fun execute(): Pager<Int, Digest> {
        return Pager(
            config = PagingConfig(pageSize = 7, enablePlaceholders = false),
            pagingSourceFactory = { DigestPagingSource(repository) }
        )
    }
}