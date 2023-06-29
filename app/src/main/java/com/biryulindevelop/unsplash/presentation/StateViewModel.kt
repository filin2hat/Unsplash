package com.biryulindevelop.unsplash.presentation

import androidx.lifecycle.ViewModel
import com.biryulindevelop.unsplash.domain.model.Profile
import com.biryulindevelop.unsplash.domain.state.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class StateViewModel @Inject constructor() : ViewModel() {

    protected val stateLoad = MutableStateFlow(LoadState.START)
    val loadState = stateLoad.asStateFlow()

    val handler = CoroutineExceptionHandler { _, _ ->
        stateLoad.value = LoadState.ERROR
    }

    private val _profile = MutableSharedFlow<Profile>()
    val profile = _profile.asSharedFlow()

    protected val query = MutableStateFlow("")

    protected var job: Job? = null

    protected val userName = MutableStateFlow("")

    protected val id = MutableStateFlow("")
}
