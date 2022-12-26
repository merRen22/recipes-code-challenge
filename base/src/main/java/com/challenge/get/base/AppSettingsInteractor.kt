package com.challenge.get.base

import com.challenge.get.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppSettingsInteractor @Inject constructor(
    private val appRepository: AppRepository,
) {

    fun hasBeenOpened(): Flow<Boolean> {
        return appRepository.hasBeenOpened()
    }

    suspend fun appOpened() {
        appRepository.saveHasBeenOpenedPreference()
    }
}