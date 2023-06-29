package com.biryulindevelop.domain.usecase

import androidx.paging.Pager
import com.biryulindevelop.domain.model.Digest

interface DigestPagingUseCase {
    fun execute(): Pager<Int, Digest>
}