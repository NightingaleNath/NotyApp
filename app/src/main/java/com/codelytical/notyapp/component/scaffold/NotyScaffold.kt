package com.codelytical.notyapp.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelytical.notyapp.component.dialog.FailureDialog
import com.codelytical.notyapp.component.dialog.LoaderDialog

/**
 * Compose's wrapped Scaffold for this project
 */
@Composable
fun NotyScaffold(
    modifier: Modifier = Modifier,
    notyTopAppBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    isLoading: Boolean = false,
    error: String? = null
) {
    if (isLoading) {
        LoaderDialog()
    }
    if (error != null) {
        FailureDialog(error)
    }
    Scaffold(
        modifier = modifier,
        topBar = notyTopAppBar,
        content = content,
        floatingActionButton = floatingActionButton
    )
}
