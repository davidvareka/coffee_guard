package cz.olc.coffee.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemUi() {
    val systemUiController = rememberSystemUiController()

    val color = Color(0xFF2b93d1)
    val darkIcons = false

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}