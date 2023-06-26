package com.biryulindevelop.unsplash.presentation.screens.user

import com.biryulindevelop.unsplash.domain.model.Profile

sealed class ProfileState {
    data class Success(
        val data: Profile
    ) : ProfileState()

    object NotStarted : ProfileState()
}
