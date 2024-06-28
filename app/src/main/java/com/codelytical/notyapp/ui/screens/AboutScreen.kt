package com.codelytical.notyapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelytical.notyapp.BuildConfig
import com.codelytical.notyapp.R
import com.codelytical.notyapp.component.scaffold.NotyScaffold
import com.codelytical.notyapp.component.scaffold.NotyTopAppBar
import com.codelytical.notyapp.utils.IntentUtils

@Composable
fun AboutScreen(onNavigateUp: () -> Unit) {
    NotyScaffold(
        notyTopAppBar = {
            NotyTopAppBar(onNavigateUp = onNavigateUp)
        },
        content = {
            AboutContent()
        }
    )
}

@Composable
fun AboutContent() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        val image = painterResource(id = R.drawable.noty_app_logo)
        Image(
            modifier = Modifier.size(92.dp, 92.dp),
            painter = image,
            contentDescription = "About Noty app",
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Noty",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle2
        )

        Spacer(modifier = Modifier.height(24.dp))

        LicenseCard()

        Spacer(modifier = Modifier.height(8.dp))

        VisitCard()
    }
}

@Composable
fun LicenseCard() {
    Card(shape = RoundedCornerShape(4.dp), elevation = 2.dp) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            val licenseTitle = stringResource(id = R.string.text_license_title)
            Text(
                text = licenseTitle,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val license = stringResource(id = R.string.text_license)
            Text(
                text = license,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun VisitCard() {
    Card(shape = RoundedCornerShape(4.dp), elevation = 2.dp) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            val visit = stringResource(id = R.string.text_visit)
            Text(
                text = visit,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val context = LocalContext.current
            val visitUrl = stringResource(id = R.string.text_repo_url)
            Box(
                Modifier.clickable(
                    onClick = {
                        IntentUtils.launchBrowser(context = context, visitUrl)
                    }
                )
            ) {
                Text(
                    text = visitUrl,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.primaryColor),
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}