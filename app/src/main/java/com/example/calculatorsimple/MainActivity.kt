package com.example.calculatorsimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorsimple.ui.theme.CalculatorSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorSimpleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var numberStr1 by remember { mutableStateOf("0") }
    var numberStr2 by remember { mutableStateOf("0") }
    var error1 by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    val buttonsEnabled by remember { mutableStateOf(!error1 && !error2) }

    val plus: (Double, Double) -> Double = { n1, n2 -> n1 + n2 }
    val minus: (Double, Double) -> Double = { n1, n2 -> n1 - n2 }
    val onTextChange1 = { text: String ->
        numberStr1 = text
        error1 = text.toDoubleOrNull() == null
    }
    val onTextChange2 = { text: String ->
        numberStr2 = text
        error2 = text.toDoubleOrNull() == null
    }
    val calculate = { operation: (Double, Double) -> Double ->
        if (error1 || error2) {
            result = "Invalid input"
        } else {
            val number1 = numberStr1.toDouble()
            val number2 = numberStr2.toDouble()
            val res: Double = operation(number1, number2)
            result = "Result: $res"
        }
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Text("Calculator", style = MaterialTheme.typography.headlineLarge)
        NumberTextField(
            value = numberStr1,
            onValueChange = onTextChange1,
            label = "Enter a number",
            error = error1
        )
        NumberTextField(
            value = numberStr2,
            onValueChange = onTextChange2,
            label = "Enter a number",
            error = error2
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            //horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onClick = { calculate(plus) },
                enabled = buttonsEnabled
            ) {
                Text("+")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onClick = { calculate(minus) },
                enabled = buttonsEnabled
            ) {
                Text("-")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onClick = { calculate { n1, n2 -> n1 * n2 } },
                enabled = buttonsEnabled
            ) {
                Text("*")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onClick = { calculate { n1, n2 -> n1 / n2 } },
                enabled = buttonsEnabled
            ) {
                Text("/")
            }
        }
        Text(result)
    }
}

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        //isError = value.toDoubleOrNull() == null
        isError = error
    )
}

@Preview(showBackground = true, widthDp = 500, heightDp = 500 )
@Composable
fun CalculatorPreview() {
    CalculatorSimpleTheme {
        Calculator()
    }
}