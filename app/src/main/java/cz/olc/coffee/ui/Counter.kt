package cz.olc.coffee.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun Counter(
    sum: Int,
    modifier: Modifier = Modifier
) {
    val sumString = "$sum Kč"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Celkem".uppercase(),
            style = TextStyle(
                fontSize = 36.sp,
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            sumString,
            style = TextStyle(fontSize = 48.sp),
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    Counter(sum = 100)
}