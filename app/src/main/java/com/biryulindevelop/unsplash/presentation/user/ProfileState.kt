package com.biryulindevelop.unsplash.presentation.user

import com.biryulindevelop.unsplash.domain.model.Profile

sealed class ProfileState {
    data class Success(val data: Profile) : ProfileState()
    object NotStartedYet : ProfileState()
}
