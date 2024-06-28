package com.codelytical.notyapp.component.text

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelytical.notyapp.ui.theme.getTextFieldHintColor

@Composable
fun NotyTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colors.onPrimary,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    helperText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(modifier) {
        Column {
            OutlinedTextField(
                value = value,
                label = { Text(text = label) },
                modifier = Modifier.fillMaxWidth().testTag(label),
                onValueChange = onValueChange,
                leadingIcon = leadingIcon,
                textStyle = TextStyle(color, fontSize = fontSize),
                isError = isError,
                visualTransformation = visualTransformation,
                shape = RoundedCornerShape(8.dp),
                trailingIcon = trailingIcon
            )
            if (helperText.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = helperText, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
fun BasicNotyTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    onTextChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChange,
        textStyle = textStyle.copy(color = MaterialTheme.colors.onPrimary),
        maxLines = maxLines,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        decorationBox = { inlineTextField ->
            AnimatedVisibility(visible = value.isBlank()) {
                Text(
                    text = label,
                    color = getTextFieldHintColor(),
                    style = textStyle
                )
            }
            inlineTextField()
        }
    )
}
