package com.codelytical.notyapp.component.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelytical.notyapp.R
import com.codelytical.notyapp.utils.NotyPreview

@Composable
fun NoteCard(title: String, note: String, isPinned: Boolean, onNoteClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onNoteClick() },
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = typography.h5,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (isPinned) {
                    Icon(
                        painterResource(id = R.drawable.ic_pinned),
                        contentDescription = "Pinned Note",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = note,
                style = typography.body1,
                color = MaterialTheme.colors.onPrimary,
                lineHeight = 24.sp,
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
fun PreviewNoteCard() = NotyPreview {
    NoteCard(
        title = "Lorem Ipsum",
        note = "Here is note body...",
        isPinned = true,
        onNoteClick = {}
    )
}
