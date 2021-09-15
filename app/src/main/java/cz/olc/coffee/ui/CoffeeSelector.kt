package cz.olc.coffee.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.ui.theme.Aqua
import cz.olc.coffee.ui.theme.Blue200
import cz.olc.coffee.ui.theme.colorChrome

@Preview
@Composable
fun CoffeePickerPreview() {
    val coffeeType = CoffeeType(
        id = 1,
        name = "Espresso Lungo",
        order = 1
    )
    CoffeeSelector(coffeeType, {})
}

@Composable
fun CoffeeSelector(
    coffeeType: CoffeeType,
    onClick: (type: CoffeeType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(115.dp)
    ) {
        Text(
            text = coffeeType.name,
            fontSize = 16.sp,
            color = Color.White,
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            lineHeight = 18.sp,
            modifier = Modifier.padding(
                horizontal = 4.dp,
            ).height(40.dp)
        )
        Spacer(Modifier.height(8.dp))
        IconButton(
            onClick = { onClick(coffeeType) },
            modifier = modifier
                .then(Modifier.size(75.dp))
                .border(6.dp, Aqua, shape = CircleShape)
        ) {
            Canvas(modifier = Modifier.size(75.dp)
                , onDraw = {
                val size = 75.dp.toPx()
                drawCircle(
                    color = colorChrome,
                    radius = size / 2f,
                )
            })
        }
    }
}
