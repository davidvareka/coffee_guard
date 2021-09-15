package cz.olc.coffee

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.ui.CoffeeTypePicker
import cz.olc.coffee.ui.Counter
import cz.olc.coffee.ui.LoadingScreen
import cz.olc.coffee.ui.SystemUi
import cz.olc.coffee.ui.models.MainViewModel
import cz.olc.coffee.ui.models.MainViewModelFactory
import cz.olc.coffee.ui.theme.CoffeeGuardTheme
import cz.olc.coffee.ui.theme.black200
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as CoffeeGuardApplication).typeRepository,
            (application as CoffeeGuardApplication).historyRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            SystemUi()
            CoffeeGuardTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    CoffeeGuardApp(viewModel, ::openHistory)
                }
            }
        }
    }

    private fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun CoffeeGuardApp(
    viewModel: MainViewModel,
    openHistoryMethod: () -> Unit,
) {
    val coffeeTypes by viewModel.coffeeTypes.observeAsState(listOf())
    val historyIsNotEmpty by viewModel.lastRecord.observeAsState(null)
    val coffeePrice by viewModel.coffeePrice.observeAsState(5)

    if (coffeeTypes.isEmpty()) {
        LoadingScreen()
    } else {
        CoffeeGuardScreen(
            coffeePrice,
            coffeeTypes,
            historyIsNotEmpty != null,
            onClick = { viewModel.storeCoffee(it) },
            onClean = { viewModel.resetCounter() },
            openHistoryMethod
        )
    }
}

@Composable
fun CoffeeGuardScreen(
    price: Int,
    coffeeTypes: List<CoffeeType>,
    historyIsNotEmpty: Boolean,
    onClick: (coffeeType: CoffeeType) -> Unit,
    onClean: () -> Unit,
    openHistoryMethod: () -> Unit,
) {
    val sum = coffeeTypes.sumOf { it.count } * price

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Counter(
                sum = sum,
            )

            if (coffeeTypes.isNotEmpty()) {
                CoffeeTypePicker(
                    coffeeTypes,
                    onClick
                )
            } else {
                Text("No data")
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { onClean() },
                enabled = sum > 0
            ) {
                Text(
                    "Resetovat",
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                enabled = historyIsNotEmpty,
                onClick = { openHistoryMethod() }
            ) {
                val alpha = if (historyIsNotEmpty) 1f else .6f

                Icon(
                    painter = painterResource(R.drawable.ic_baseline_history_36),
                    "history",
                    modifier = Modifier
                        .size(36.dp)
                        .alpha(alpha)
                )
            }
        }
    }
}

@Preview
@Composable
fun CoffeeGuardPreview() {
    val types = listOf(
        CoffeeType(
            id = 1,
            name = "Espresso",
            order = 1,
            count = 1,
        ),
        CoffeeType(
            id = 2,
            name = "Espresso\ns mlekem",
            order = 2,
            count = 2,
        ),
        CoffeeType(
            id = 3,
            name = "Lungo",
            order = 3,
            count = 3,
        ),
    )

    CoffeeGuardScreen(5, types, false, {}, {}, {})
}