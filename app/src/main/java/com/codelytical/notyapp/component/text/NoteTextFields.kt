package com.codelytical.notyapp.component.text

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codelytical.notyapp.component.text.BasicNotyTextField

@Composable
fun NoteTitleField(
    modifier: Modifier = Modifier,
    value: String = "",
    onTextChange: (String) -> Unit
) {
    BasicNotyTextField(
        modifier,
        value = value,
        label = "Title",
        onTextChange = onTextChange,
        textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
        maxLines = 2
    )
}

@Composable
fun NoteField(
    modifier: Modifier = Modifier,
    value: String = "",
    onTextChange: (String) -> Unit
) {
    BasicNotyTextField(
        modifier,
        value = value,
        label = "Write note here",
        onTextChange = onTextChange,
        textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light)
    )
}
