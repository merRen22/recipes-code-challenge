package com.challenge.get.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.challenge.get.base.compose.ChallengeTheme
import com.challenge.get.base.viewmodel.MainActivityViewModel
import com.challenge.get.base.viewmodel.MainActivityViewModel.UiState.Loading
import com.challenge.get.base.viewmodel.MainActivityViewModel.UiState.Success
import com.challenge.get.base.viewmodel.MainActivityViewModel.UiState
import com.challenge.get.navigation.BaseViewModelFactoryProvider
import com.challenge.get.navigation.ChallengeNavHost
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider : BaseViewModelFactoryProvider

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: UiState = Loading

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                Loading -> true
                Success -> false
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }.collect()
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ChallengeTheme{ ChallengeNavHost() }
        }
    }
}