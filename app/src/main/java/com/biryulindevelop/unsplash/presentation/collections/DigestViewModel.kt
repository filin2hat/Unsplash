package com.biryulindevelop.unsplash.presentation.collections

import com.biryulindevelop.unsplash.domain.usecase.DigestPagingUseCase
import com.biryulindevelop.unsplash.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DigestViewModel @Inject constructor(
    private val digestPagingUseCase: DigestPagingUseCase
) : BaseViewModel() {

    fun getDigest() = digestPagingUseCase.getDigest().flow
}