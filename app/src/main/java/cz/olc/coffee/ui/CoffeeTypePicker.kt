package cz.olc.coffee.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.olc.coffee.data.CoffeeType

@Preview
@Composable
fun CoffeeTypesPickerPreview() {
    val types = listOf(
        CoffeeType(
            id = 1,
            name = "Espresso",
            order = 1
        ),
        CoffeeType(
            id = 2,
            name = "Espresso\ns mlekem",
            order = 2
        ),
        CoffeeType(
            id = 3,
            name = "Lungo",
            order = 3
        ),
        CoffeeType(
            id = 4,
            name = "Mochaccino",
            order = 4
        ),
        CoffeeType(
            id = 5,
            name = "Caffe latte",
            order = 5
        ),
        CoffeeType(
            id = 6,
            name = "Cokoloda",
            order = 6
        ),
    )

    CoffeeTypePicker(types) {}
}

@Composable
fun CoffeeTypePicker(
    coffeeTypes: List<CoffeeType>,
    onClick: (type: CoffeeType) -> Unit
) {
    Column {
        Spacer(Modifier.weight(1f))

        val typesToRender = mutableListOf<CoffeeType>()
        var i = 0;
        coffeeTypes.forEach { type ->
            i++;
            typesToRender.add(type)
            if (i % 3 == 0) {
                CoffeeTypePickerRow(typesToRender, onClick)
                Spacer(Modifier.height(24.dp))
                typesToRender.clear()
            }
        }

        if (typesToRender.size > 0) {
            CoffeeTypePickerRow(typesToRender, onClick)
        }

        Spacer(Modifier.weight(2f))
    }
}

@Composable
fun CoffeeTypePickerRow(coffeeTypes: List<CoffeeType>, onClick: (type: CoffeeType) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        coffeeTypes.forEach {
            CoffeeSelector(
                it,
                onClick
            )
        }
    }
}