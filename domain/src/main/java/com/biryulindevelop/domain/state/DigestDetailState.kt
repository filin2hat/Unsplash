package com.biryulindevelop.domain.state

import com.biryulindevelop.domain.model.Digest

sealed class DigestState {
    data class Success(val data: Digest) : DigestState()
    object NotStarted : DigestState()
}
