package com.biryulindevelop.unsplash.presentation.photos.details

import com.biryulindevelop.unsplash.domain.model.PhotoDetails

sealed class DetailsState {
    data class Success(val data: PhotoDetails) : DetailsState()
    object NotStartedYet : DetailsState()
}
