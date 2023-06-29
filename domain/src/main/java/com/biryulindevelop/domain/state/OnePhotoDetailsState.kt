package com.biryulindevelop.domain.state

import com.biryulindevelop.domain.model.PhotoDetails

sealed class OnePhotoDetailsState {
    data class Success(val data: PhotoDetails) : OnePhotoDetailsState()
    object NotStarted : OnePhotoDetailsState()
}
