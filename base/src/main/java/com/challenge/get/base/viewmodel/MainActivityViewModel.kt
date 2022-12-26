package com.challenge.get.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.get.base.AppSettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    appSettings: AppSettingsInteractor
) : ViewModel() {
    val uiState: StateFlow<UiState> = appSettings.hasBeenOpened().map {
        if (!it) {
            appSettings.appOpened()
        }
        UiState.Success
    }.stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.WhileSubscribed(1_000)
    )

    sealed interface UiState {
        object Loading : UiState
        object Success : UiState
    }
}