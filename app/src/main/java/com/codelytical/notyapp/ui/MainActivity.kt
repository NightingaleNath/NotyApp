package com.codelytical.notyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.codelytical.core.preference.PreferenceManager
import com.codelytical.notyapp.R
import com.codelytical.notyapp.navigation.NotyNavigation
import com.codelytical.notyapp.ui.theme.NotyTheme
import com.codelytical.notyapp.view.viewmodel.NoteDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun noteDetailViewModelFactory(): NoteDetailViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContent {
            NotyMain()
        }
        observeUiTheme()
    }

    @Composable
    private fun NotyMain() {
        val darkMode by rememberUiMode()

        NotyTheme(darkTheme = darkMode) {
            Surface {
                NotyNavigation()
            }
        }
    }

    @Composable
    fun rememberUiMode(): State<Boolean> {
        return preferenceManager.uiModeFlow.collectAsState(initial = false)
    }

    private fun observeUiTheme() {
        preferenceManager
            .uiModeFlow
            .flowWithLifecycle(lifecycle)
            .onEach { isDarkMode ->
                val mode = if (isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }.launchIn(lifecycleScope)
    }
}