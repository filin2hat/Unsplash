package com.biryulindevelop.unsplash.presentation.screens.photos.details

import androidx.lifecycle.viewModelScope
import com.biryulindevelop.domain.model.PhotoDetails
import com.biryulindevelop.domain.state.LoadState
import com.biryulindevelop.domain.state.OnePhotoDetailsState
import com.biryulindevelop.domain.usecase.LikeDetailPhotoUseCase
import com.biryulindevelop.domain.usecase.OnePhotoDetailsUseCase
import com.biryulindevelop.unsplash.presentation.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnePhotoDetailsViewModel @Inject constructor(
    private val onePhotoDetailsUseCase: OnePhotoDetailsUseCase,
    private val likeDetailPhotoUseCase: LikeDetailPhotoUseCase
) : StateViewModel() {

    private val _state = MutableStateFlow<OnePhotoDetailsState>(OnePhotoDetailsState.NotStarted)
    val state = _state.asStateFlow()
    var success = false

    fun loadPhotoDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            stateLoad.value = LoadState.SUCCESS
            _state.value = OnePhotoDetailsState.Success(onePhotoDetailsUseCase.execute(id = id))
        }
    }

    fun like(item: PhotoDetails) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            likeDetailPhotoUseCase.execute(item)
            stateLoad.value = LoadState.SUCCESS
            _state.value =
                OnePhotoDetailsState.Success(onePhotoDetailsUseCase.execute(id = item.id))
        }
    }
}