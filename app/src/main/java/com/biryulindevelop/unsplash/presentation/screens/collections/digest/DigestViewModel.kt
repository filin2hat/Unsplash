package com.biryulindevelop.unsplash.presentation.screens.collections.digest

import com.biryulindevelop.unsplash.domain.usecase.interfaceces.DigestPagingUseCase
import com.biryulindevelop.unsplash.presentation.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DigestViewModel @Inject constructor(
    private val digestPagingUseCase: DigestPagingUseCase
) : StateViewModel() {
    fun getDigest() = digestPagingUseCase.execute().flow
}
