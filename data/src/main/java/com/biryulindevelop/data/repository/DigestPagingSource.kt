package com.biryulindevelop.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import javax.inject.Inject

class DigestPagingSource @Inject constructor(
    private val repository: DigestRemoteRepository
) : PagingSource<Int, Digest>() {

    override fun getRefreshKey(state: PagingState<Int, Digest>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Digest> {
        val currentPage = params.key ?: FIRST_PAGE
        return runCatching {
            repository.getDigests(currentPage)
        }.fold(
            onSuccess = { digests ->
                LoadResult.Page(
                    data = digests,
                    prevKey = if (currentPage == FIRST_PAGE) null else currentPage - 1,
                    nextKey = if (digests.isEmpty()) null else currentPage + 1
                )
            },
            onFailure = { throwable ->
                LoadResult.Error(throwable)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
