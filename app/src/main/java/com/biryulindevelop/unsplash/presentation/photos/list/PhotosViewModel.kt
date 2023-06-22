package com.biryulindevelop.unsplash.presentation.photos.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotoLikeUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotosPagingUseCase
import com.biryulindevelop.unsplash.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

    private val query = MutableStateFlow("")
    private var job: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto() = query.asStateFlow()
        .flatMapLatest { photosPagingUseCase.execute(Requester.ALL_LIST.apply { param = it }) }
        .cachedIn(CoroutineScope(Dispatchers.IO))


    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.execute(item)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun setQuery(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            query.value = newText
            refresh()
        }
    }
}