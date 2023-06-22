package com.biryulindevelop.unsplash.presentation.user

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.model.Profile
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetProfileUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotoLikeUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotosPagingUseCase
import com.biryulindevelop.unsplash.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

    private val userName = MutableStateFlow("")
    private var job: Job? = null

    private val _profile = MutableSharedFlow<Profile>()
    val profile = _profile.asSharedFlow()

    private val _state = MutableStateFlow<ProfileState>(ProfileState.NotStartedYet)
    val state = _state.asStateFlow()

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            _loadState.value = LoadState.SUCCESS
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
            _loadState.value = LoadState.SUCCESS
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