package cz.olc.coffee

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.olc.coffee.data.HistoryRecord
import cz.olc.coffee.ui.LoadingScreen
import cz.olc.coffee.ui.NoDataScreen
import cz.olc.coffee.ui.models.HistoryViewModel
import cz.olc.coffee.ui.models.HistoryViewModelFactory
import cz.olc.coffee.ui.theme.CoffeeGuardTheme
import cz.olc.coffee.ui.theme.colorChrome
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import cz.olc.coffee.ui.SystemUi
import cz.olc.coffee.ui.theme.colorChromeOpaque

class HistoryActivity : ComponentActivity() {
    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(
            (application as CoffeeGuardApplication).historyRepository,
        )
    }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemUi()
            CoffeeGuardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        HistoryScreen(viewModel) { onClean() }
                    }
                }
            }
        }
    }

    @ExperimentalTime
    private fun onClean() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            finish()
        }, 1000)
    }
}

@Composable
fun HistoryScreen(viewModel: HistoryViewModel, onClean: () -> Unit) {
    val history by viewModel.history.observeAsState(null)

    if (history == null) {
        LoadingScreen()
        return
    }

    history?.let {
        if (it.isEmpty()) {
            NoDataScreen()
        } else {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                HistoryList(
                    it,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = Color(54, 54, 54),
                    elevation = 5.dp
                ) {
                    Column {
                        Spacer(modifier = Modifier
                            .height(2.dp)
                            .background(MaterialTheme.colors.primary)
                            .fillMaxWidth()
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.clear()
                                    onClean()
                                },
                                enabled = it.isNotEmpty()
                            ) {
                                Text(
                                    "Vymazat historii",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryList(
    records: List<HistoryRecord>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(records) { record ->
            val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date: Date? = df.parse(record.date)
            val formatDate: String =
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date?.time)

            HistoryRecord(record, formatDate)
        }
    }
}

@Composable
fun HistoryRecord(
    record: HistoryRecord,
    recordDate: String
) {
    val fontSize = 16.sp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            recordDate,
            fontSize = fontSize,
            modifier = Modifier.width(85.dp)
        )

        Text(
            record.coffeeTypeName.replace("\n", "").replace("|", ", "),
            fontSize = fontSize,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorChromeOpaque)
    )
}