package com.codelytical.notyapp.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.codelytical.notyapp.R
import com.codelytical.notyapp.ui.theme.typography
import com.codelytical.notyapp.component.anim.LottieAnimation

@Composable
fun LoaderDialog() {
    Dialog(onDismissRequest = {}) {
        Surface(modifier = Modifier.size(128.dp)) {
            LottieAnimation(
                resId = R.raw.loading,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun FailureDialog(failureMessage: String, onDialogDismiss: () -> Unit = {}) {
    Dialog(onDismissRequest = onDialogDismiss) {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LottieAnimation(
                    resId = R.raw.failure,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(84.dp)
                )
                Text(
                    text = failureMessage,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    onClick = onDialogDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp)

                ) {
                    Text(style = typography.subtitle1, color = Color.White, text = "OK")
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onConfirmedYes: () -> Unit,
    onConfirmedNo: () -> Unit,
    onDismissed: () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }

    if (!isDismissed) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth(),
            onDismissRequest = onDismissed,
            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text = message,
                    fontSize = 15.sp
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            onConfirmedYes()
                            isDismissed = true
                        },
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Yes",
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    TextButton(
                        onClick = {
                            onConfirmedNo()
                            isDismissed = true
                        },
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "No",
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        )
    }
}
