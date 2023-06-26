package com.biryulindevelop.unsplash.presentation.screens.collections.details

import com.biryulindevelop.unsplash.domain.model.Digest

sealed class DigestState {
    data class Success(val data: Digest) : DigestState()
    object NotStarted : DigestState()
}
