package cz.olc.coffee.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cz.olc.coffee.ui.theme.black200

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Načítám...",
            color = black200,
            fontSize = 24.sp,
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Composable
fun NoDataScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Žádná data",
            color = black200,
            fontSize = 24.sp,
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
    }
}

@Preview
@Composable
fun NoDataScreenPreview() {
    NoDataScreen()
}