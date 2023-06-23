package com.biryulindevelop.unsplash.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetDigestInfoUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotoLikeUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotosPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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
) : ViewModel() {
    private val _loadState = MutableStateFlow(LoadState.START)
    val loadState = _loadState.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, _ ->
        _loadState.value = LoadState.ERROR
    }

    private val id = MutableStateFlow("")
    private var job: Job? = null

    private val _state = MutableStateFlow<DigestState>(DigestState.NotStartedYet)
    val state = _state.asStateFlow()

    fun getDigestInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val a = getDigestInfoUseCase.execute(id = id)
            _loadState.value = LoadState.SUCCESS
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
            _loadState.value = LoadState.SUCCESS
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
