package com.codelytical.notyapp.component.action

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codelytical.notyapp.R
import com.codelytical.notyapp.utils.collection.ComposeImmutableList

@Composable
fun PinAction(isPinned: Boolean, onClick: () -> Unit) {
    val (icon, contentDescription) = if (isPinned) {
        R.drawable.ic_pinned to "Pinned"
    } else {
        R.drawable.ic_unpinned to "Not Pinned"
    }
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(8.dp)
                .testTag("actionTogglePin")
        )
    }
}

@Composable
fun DeleteAction(onClick: () -> Unit) {
    val icon = painterResource(R.drawable.ic_delete)
    IconButton(onClick = onClick) {
        Icon(
            painter = icon,
            contentDescription = "Delete",
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun ShareAction(onClick: () -> Unit) {
    val icon = painterResource(R.drawable.ic_share)
    IconButton(onClick = onClick) {
        Icon(
            icon,
            "share",
            Modifier
                .padding(8.dp)
        )
    }
}

@Immutable
data class ShareActionItem(
    val label: String,
    val onActionClick: () -> Unit
)

@Composable
fun ShareDropdown(
    expanded: Boolean,
    shareActions: ComposeImmutableList<ShareActionItem>,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .wrapContentHeight()
            .width(100.dp)
    ) {
        shareActions.forEach { shareAction ->
            DropdownMenuItem(
                onClick = {
                    shareAction.onActionClick.invoke()
                    onDismissRequest.invoke()
                }
            ) {
                Row {
                    Text(text = shareAction.label)
                }
            }
        }
    }
}

@Composable
fun ThemeSwitchAction(onToggle: () -> Unit) {
    val icon = painterResource(R.drawable.ic_day)
    IconButton(onClick = onToggle) {
        Icon(icon,
            "Theme switch",
            Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LogoutAction(onLogout: () -> Unit) {
    val icon = painterResource(R.drawable.ic_logout)
    IconButton(onClick = onLogout) {
        Icon(
            icon,
            "Logout",
            Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun AboutAction(onClick: () -> Unit) {
    val icon = painterResource(R.drawable.ic_baseline_info)
    IconButton(onClick = onClick) {
        Icon(
            icon,
            "About",
            Modifier
                .padding(8.dp)
        )
    }
}
