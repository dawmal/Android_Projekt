package com.example.paragon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.round

val checkG = ArrayList<Boolean?>()

@Composable
fun TextOutput(modifier: Modifier = Modifier, navController: NavController) {
    var suma by remember {
        mutableDoubleStateOf(0.0)
    }
    var sumaChecked by remember {
        mutableDoubleStateOf(0.0)
    }
    val check = remember {
        mutableStateListOf<Boolean?>().apply { addAll(List(name.count()) { false }) }
    }

    LaunchedEffect(prize) {
        prize.forEach{prize ->
            suma += prize.replace(",", ".").toDouble()
        }
        suma = round(suma*100) /100
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            //verticalAlignment = Alignment.Top,
            //horizontalArrangement  =  Arrangement.SpaceAround
        ) {
            name.forEachIndexed { index, name ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement  =  Arrangement.SpaceAround,
                ) {
                    TriStateCheckbox(
                        state = when (check[index]) {
                            true -> ToggleableState.On
                            false -> ToggleableState.Off
                            null -> ToggleableState.Indeterminate
                        },
                        onClick = {
                            val prizeValue = prize[index].replace(",", ".").toDouble()

                            when (check[index]) {
                                true -> sumaChecked -= prizeValue
                                null -> sumaChecked -= prizeValue / 2
                                false -> {} // false nic nie zmienia w `sumaChecked`
                            }

                            check[index] = when (check[index]) {
                                false -> true
                                true -> null
                                null -> false
                            }

                            when (check[index]) {
                                true -> sumaChecked += prizeValue
                                null -> sumaChecked += prizeValue / 2
                                false -> {} // false nic nie zmienia w `sumaChecked`
                            }
                        })

                    Text(
                        text = name,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = prize[index],
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 5.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        //sumaChecked = round(sumaChecked * 100) / 100
        Text(text = "Suma wszystkich: ${suma.toString().replace(".", ",")}")
        Text(text = "Suma zaznaczonych: ${(round(sumaChecked*100)/100).toString().replace(".", ",")}")

        Button(onClick = {
            checkG.clear()
            checkG.addAll(check)
            navController.navigate("Category")
        }) {
            Text(text = "Dalej")
        }
    }
}