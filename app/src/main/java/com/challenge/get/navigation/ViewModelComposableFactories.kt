package com.challenge.get.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.challenge.get.detail.DetailViewModel
import com.challenge.get.ui.activity.MainActivity
import dagger.hilt.android.EntryPointAccessors

interface BaseViewModelFactoryProvider {
    fun getDetailsViewModelFactory(): DetailViewModel.Factory
}

@Composable
fun detailViewModel(id: String): DetailViewModel = viewModel(
    factory = DetailViewModel.provideFactory(
        getViewModelFactoryProvider().getDetailsViewModelFactory(),
        id
    )
)

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    LocalContext.current as Activity,
    MainActivity.ViewModelFactoryProvider::class.java
)