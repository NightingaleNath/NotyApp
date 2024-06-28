package com.codelytical.notyapp.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codelytical.notyapp.view.state.State
import com.codelytical.notyapp.view.viewmodel.BaseViewModel

/**
 * Collects values from this ViewModel's state and represents its latest value via State.
 * Every time there would be new value posted into the state the returned State will be
 * updated causing recomposition of every State.value usage.
 */
@Composable
fun <S : State, VM : BaseViewModel<S>> VM.collectState() = state.collectAsStateWithLifecycle()
