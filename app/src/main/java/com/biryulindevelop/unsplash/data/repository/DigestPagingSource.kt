package com.biryulindevelop.unsplash.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import javax.inject.Inject

class DigestPagingSource @Inject constructor(
    private val repository: DigestRemoteRepository
) : PagingSource<Int, Digest>() {

    override fun getRefreshKey(state: PagingState<Int, Digest>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Digest> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getDigests(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            }, onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}
