package com.biryulindevelop.unsplash.presentation.collections

import androidx.lifecycle.ViewModel
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.DigestPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DigestViewModel @Inject constructor(
    private val digestPagingUseCase: DigestPagingUseCase
) : ViewModel() {

    private val _loadState = MutableStateFlow(LoadState.START)
    val loadState = _loadState.asStateFlow()

    fun getDigest() = digestPagingUseCase.execute().flow
}
