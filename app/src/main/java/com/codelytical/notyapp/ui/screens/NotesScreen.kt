package com.codelytical.notyapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.codelytical.core.model.Note
import com.codelytical.notyapp.component.action.AboutAction
import com.codelytical.notyapp.component.action.LogoutAction
import com.codelytical.notyapp.component.action.ThemeSwitchAction
import com.codelytical.notyapp.component.dialog.ConfirmationDialog
import com.codelytical.notyapp.component.note.NotesList
import com.codelytical.notyapp.component.scaffold.NotyScaffold
import com.codelytical.notyapp.component.scaffold.NotyTopAppBar
import com.codelytical.notyapp.utils.collectState
import com.codelytical.notyapp.utils.collection.ComposeImmutableList
import com.codelytical.notyapp.utils.collection.rememberComposeImmutableList
import com.codelytical.notyapp.view.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNavigateToAbout: () -> Unit,
    onNavigateToAddNote: () -> Unit,
    onNavigateToNoteDetail: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.collectState()

    val isInDarkMode by viewModel.preferenceManager.uiModeFlow.collectAsState(initial = false)

    var showLogoutConfirmation by remember { mutableStateOf(false) }

    val notes by rememberComposeImmutableList { state.notes }

    NotesContent(
        isLoading = state.isLoading,
        notes = notes,
        onRefresh = {},
        onToggleTheme = { viewModel.setDarkMode(!isInDarkMode) },
        onAboutClick = onNavigateToAbout,
        onAddNoteClick = onNavigateToAddNote,
        onLogoutClick = { showLogoutConfirmation = true },
        onNavigateToNoteDetail = onNavigateToNoteDetail
    )

    LogoutConfirmation(
        show = showLogoutConfirmation,
        onConfirm = viewModel::logout,
        onDismiss = { showLogoutConfirmation = false }
    )

    val isUserLoggedIn = state.isUserLoggedIn
    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn == false) {
            onNavigateToLogin()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesContent(
    isLoading: Boolean,
    notes: ComposeImmutableList<Note>,
    error: String? = null,
    onRefresh: () -> Unit,
    onToggleTheme: () -> Unit,
    onAboutClick: () -> Unit,
    onAddNoteClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNavigateToNoteDetail: (String) -> Unit
) {
    NotyScaffold(
        error = error,
        notyTopAppBar = {
            NotyTopAppBar(
                actions = {
                    ThemeSwitchAction(onToggleTheme)
                    AboutAction(onAboutClick)
                    LogoutAction(onLogout = onLogoutClick)
                }
            )
        },
        content = {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = onRefresh
            )
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
            ) {
                Column {
                    NotesList(notes) { note -> onNavigateToNoteDetail(note.id) }
                }

                PullRefreshIndicator(
                    refreshing = isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Add",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun LogoutConfirmation(show: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (show) {
        ConfirmationDialog(
            title = "Logout?",
            message = "Sure want to logout?",
            onConfirmedYes = onConfirm,
            onConfirmedNo = onDismiss,
            onDismissed = onDismiss
        )
    }
}
