package com.biryulindevelop.unsplash.presentation.collections

import com.biryulindevelop.unsplash.domain.model.Digest

sealed class DigestState {
    data class Success(val data: Digest) : DigestState()
    object NotStartedYet : DigestState()
}
