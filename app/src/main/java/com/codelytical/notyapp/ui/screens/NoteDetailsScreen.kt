
package com.codelytical.notyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codelytical.notyapp.component.action.DeleteAction
import com.codelytical.notyapp.component.action.PinAction
import com.codelytical.notyapp.component.action.ShareAction
import com.codelytical.notyapp.component.action.ShareActionItem
import com.codelytical.notyapp.component.action.ShareDropdown
import com.codelytical.notyapp.component.dialog.ConfirmationDialog
import com.codelytical.notyapp.component.scaffold.NotyScaffold
import com.codelytical.notyapp.component.scaffold.NotyTopAppBar
import com.codelytical.notyapp.component.text.NoteField
import com.codelytical.notyapp.component.text.NoteTitleField
import com.codelytical.notyapp.utils.collectState
import com.codelytical.notyapp.utils.collection.composeImmutableListOf
import com.codelytical.notyapp.utils.saveBitmap
import com.codelytical.notyapp.utils.shareImage
import com.codelytical.notyapp.utils.shareNoteText
import com.codelytical.notyapp.view.viewmodel.NoteDetailViewModel
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun NoteDetailsScreen(
    viewModel: NoteDetailViewModel,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.collectState()
    val context = LocalContext.current

    var showDeleteNoteConfirmation by remember { mutableStateOf(false) }

    NoteDetailContent(
        title = state.title ?: "",
        note = state.note ?: "",
        error = state.error,
        isPinned = state.isPinned,
        showSaveButton = state.showSave,
        onTitleChange = viewModel::setTitle,
        onNoteChange = viewModel::setNote,
        onPinClick = viewModel::togglePin,
        onSaveClick = viewModel::save,
        onDeleteClick = { showDeleteNoteConfirmation = true },
        onNavigateUp = onNavigateUp,
        onShareNoteAsText = { context.shareNoteText(state.title ?: "", state.note ?: "") },
        onShareNoteAsImage = { bitmap ->
            val uri = saveBitmap(context, bitmap.asAndroidBitmap())
            if (uri != null) {
                context.shareImage(uri)
            }
        }
    )

    DeleteNoteConfirmation(
        show = showDeleteNoteConfirmation,
        onConfirm = viewModel::delete,
        onDismiss = { showDeleteNoteConfirmation = false }
    )

    LaunchedEffect(state.finished) {
        if (state.finished) {
            onNavigateUp()
        }
    }
}

@Composable
fun NoteDetailContent(
    title: String,
    note: String,
    error: String?,
    isPinned: Boolean,
    showSaveButton: Boolean,
    onTitleChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onPinClick: () -> Unit,
    onSaveClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareNoteAsText: () -> Unit,
    onShareNoteAsImage: (ImageBitmap) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val captureController = rememberCaptureController()

    NotyScaffold(
        error = error,
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusable(true),
        notyTopAppBar = {
            NotyTopAppBar(
                onNavigateUp = onNavigateUp,
                actions = {
                    NoteDetailActions(
                        isPinned = isPinned,
                        onPinClick = onPinClick,
                        onDeleteClick = onDeleteClick,
                        onShareNoteAsTextClick = onShareNoteAsText,
                        onShareNoteAsImageClick = {
                            focusRequester.requestFocus()
                            captureController.capture()
                        }
                    )
                }
            )
        },
        content = {
            NoteDetailBody(
                captureController = captureController,
                onCaptured = onShareNoteAsImage,
                title = title,
                onTitleChange = onTitleChange,
                note = note,
                onNoteChange = onNoteChange
            )
        },
        floatingActionButton = {
            if (showSaveButton) {
                ExtendedFloatingActionButton(
                    text = { Text("Save", color = Color.White) },
                    icon = { Icon(Icons.Filled.Done, "Save", tint = Color.White) },
                    onClick = onSaveClick,
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        }
    )
}

@Composable
private fun NoteDetailActions(
    isPinned: Boolean,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareNoteAsTextClick: () -> Unit,
    onShareNoteAsImageClick: () -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val shareActions = remember {
        composeImmutableListOf(
            ShareActionItem(
                label = "Text",
                onActionClick = onShareNoteAsTextClick
            ),
            ShareActionItem(
                label = "Image",
                onActionClick = onShareNoteAsImageClick
            )
        )
    }

    PinAction(isPinned, onClick = onPinClick)
    DeleteAction(onClick = onDeleteClick)
    ShareAction(onClick = { dropdownExpanded = true })
    ShareDropdown(
        expanded = dropdownExpanded,
        onDismissRequest = { dropdownExpanded = false },
        shareActions = shareActions
    )
}

@Composable
private fun NoteDetailBody(
    captureController: CaptureController,
    onCaptured: (ImageBitmap) -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit
) {
    Capturable(
        controller = captureController,
        onCaptured = { bitmap, _ -> bitmap?.let(onCaptured) }
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            NoteTitleField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                value = title,
                onTextChange = onTitleChange
            )

            NoteField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp)
                    .background(MaterialTheme.colors.background),
                value = note,
                onTextChange = onNoteChange
            )
        }
    }
}

@Composable
fun DeleteNoteConfirmation(show: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (show) {
        ConfirmationDialog(
            title = "Delete?",
            message = "Sure want to delete this note?",
            onConfirmedYes = onConfirm,
            onConfirmedNo = onDismiss,
            onDismissed = onDismiss
        )
    }
}
