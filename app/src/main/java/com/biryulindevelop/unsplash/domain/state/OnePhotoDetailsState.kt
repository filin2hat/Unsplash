package com.biryulindevelop.unsplash.domain.state

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

sealed class OnePhotoDetailsState {
    data class Success(val data: PhotoDetails) : OnePhotoDetailsState()
    object NotStarted : OnePhotoDetailsState()
}
