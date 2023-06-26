package com.biryulindevelop.unsplash.presentation.screens.photos.details

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

sealed class OnePhotoDetailsState {
    data class Success(val data: PhotoDetails) : OnePhotoDetailsState()
    object NotStarted : OnePhotoDetailsState()
}
