package com.biryulindevelop.unsplash.presentation.screens.collections.details

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.state.DigestState
import com.biryulindevelop.domain.state.LoadState
import com.biryulindevelop.domain.state.Requester
import com.biryulindevelop.domain.usecase.GetDigestInfoUseCase
import com.biryulindevelop.domain.usecase.PhotoLikeUseCase
import com.biryulindevelop.domain.usecase.PhotosPagingUseCase
import com.biryulindevelop.unsplash.presentation.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigestDetailsViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase,
    private val getDigestInfoUseCase: GetDigestInfoUseCase,
) : StateViewModel() {

    private val _state = MutableStateFlow<DigestState>(DigestState.NotStarted)
    val state = _state.asStateFlow()

    fun getDigestInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val a = getDigestInfoUseCase.execute(id = id)
            stateLoad.value = LoadState.SUCCESS
            _state.value = DigestState.Success(a)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto() = id.asStateFlow()
        .flatMapLatest { photosPagingUseCase.execute(Requester.COLLECTIONS.apply { param = it }) }
        .cachedIn(CoroutineScope(Dispatchers.IO))


    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.execute(item)
            stateLoad.value = LoadState.SUCCESS
        }
    }

    fun setId(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            id.value = newText
            refresh()
        }
    }
}
