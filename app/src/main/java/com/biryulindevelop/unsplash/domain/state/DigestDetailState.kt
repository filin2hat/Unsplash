package com.biryulindevelop.unsplash.domain.state

import com.biryulindevelop.unsplash.domain.model.Digest

sealed class DigestState {
    data class Success(val data: Digest) : DigestState()
    object NotStarted : DigestState()
}
