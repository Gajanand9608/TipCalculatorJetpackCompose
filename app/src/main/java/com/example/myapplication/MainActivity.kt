package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.InputField
import com.example.myapplication.ui.theme.TipCalculatorTheme
import com.example.myapplication.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                TopHeader()
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    TipCalculatorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(150.dp)
//            shape = RoundedCornerShape(corner = CornerSize(12.dp))
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "$$total",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val sliderPositionState = remember {
        mutableStateOf(0.0)
    }
    val splitValueState = remember {
        mutableStateOf(1)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val perPersonTipAmountState = remember {
        mutableStateOf(0.0)
    }
    val range = IntRange(start = 1, endInclusive = 100)
    Column {
        BillForm(
            splitByState = splitValueState,
            tipAmountState = tipAmountState,
            totalPerPersonState = perPersonTipAmountState
        ) {}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
//    val splitValueState = remember {
//        mutableStateOf(1)
//    }
//    val tipAmountState = remember {
//        mutableStateOf(0f)
//    }
//    val perPersonTipAmountState = remember {
//        mutableStateOf(0f)
//    }

    var tipPercentage = (sliderPositionState.value * 100).toInt()
    val keyboardController = LocalSoftwareKeyboardController.current
    TopHeader(totalPerPersonState.value.toDouble())
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    // TODO on value changed
                    onValChange(totalBillState.value.trim())
                    keyboardController!!.hide()
                }
            )
            if (validState) {
                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split", modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(140.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                            if (splitByState.value > 1)
                                splitByState.value = splitByState.value - 1
                            else {
                                splitByState.value = 1
                            }
                        })
                        Text(
                            text = splitByState.value.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                            if (splitByState.value < range.last)
                                splitByState.value += 1
                        })
                    }

                }
                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(
                        text = "$${tipAmountState.value}",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )

                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage %")
                    Spacer(modifier = Modifier.height(15.dp))
                    Slider(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        value = sliderPositionState.value,
                        onValueChange = { newVal ->
                            sliderPositionState.value = newVal
                            tipPercentage = (sliderPositionState.value * 100).toInt()
                            tipAmountState.value = calculateTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                            totalPerPersonState.value = totalBillPerPerson(
                                totalBillState.value.toDouble(),
                                splitByState.value,
                                tipAmountState.value
                            )
                        },
                        steps = 5
                    )

                }
            } else {
                Box() {}
            }
        }
    }


}

fun calculateTip(totalBill: Double, tipPercentage: Int): Double {
    return if (totalBill > 1 && totalBill.toString().isNotEmpty()) {
        (totalBill * tipPercentage) / 100
    } else {
        0.0
    }
}

fun totalBillPerPerson(totalBill: Double, splitBy: Int, totalTip: Double): Double {
    return (totalBill + totalTip) / splitBy
}

@Preview(showBackground = true)
@Composable
fun TipPreview() {
    TipCalculatorTheme {
        Text(text = "Hello again")
    }
}