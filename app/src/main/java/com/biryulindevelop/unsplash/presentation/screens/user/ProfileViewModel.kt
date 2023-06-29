package com.biryulindevelop.unsplash.presentation.screens.user

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.state.LoadState
import com.biryulindevelop.unsplash.domain.state.ProfileState
import com.biryulindevelop.unsplash.domain.state.Requester
import com.biryulindevelop.unsplash.domain.usecase.GetProfileUseCase
import com.biryulindevelop.unsplash.domain.usecase.PhotoLikeUseCase
import com.biryulindevelop.unsplash.domain.usecase.PhotosPagingUseCase
import com.biryulindevelop.unsplash.presentation.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : StateViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.NotStarted)
    val state = _state.asStateFlow()

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            stateLoad.value = LoadState.SUCCESS
            _state.value = ProfileState.Success(getProfileUseCase.execute())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto() = userName.asStateFlow()
        .flatMapLatest { photosPagingUseCase.execute(Requester.PROFILE.apply { param = it }) }
        .cachedIn(CoroutineScope(Dispatchers.IO))

    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.execute(item)
            stateLoad.value = LoadState.SUCCESS
        }
    }

    fun setUsername(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            userName.value = newText
            refresh()
        }
    }
}