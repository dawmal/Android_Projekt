package com.example.projekt1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.projekt1.ui.theme.Projekt1Theme
import com.example.projekt1.ui.theme.Purple40
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projekt1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = EkranA(
                        imie = "",
                        indeks = ""
                    )
                ){
                    composable<EkranA> {
                        val args = it.toRoute<EkranA>()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Purple40),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Imie: ${args.imie}, numer indeksu: ${args.indeks}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            val imie = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = imie.value,
                                onValueChange = {imie.value = it},
                                placeholder = { Text(text = "Imie:")}
                                //label = {Text(text = "Imie:") }
                                )

                            val indeks = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = indeks.value,
                                onValueChange = { if(it.isDigitsOnly()) indeks.value = it},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                placeholder = {Text(text = "Numer indeksu:")})

                            Row{
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    modifier = Modifier.padding(end = 25.dp),
                                    onClick = {
                                    navController.navigate(EkranB(
                                        imie = imie.value,
                                        indeks = indeks.value
                                    ))
                                }) {
                                    Text(
                                        color = Color.Black,
                                        text = "Ekran B"
                                    )
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                    onClick = {
                                    navController.navigate(EkranC(
                                        imie = imie.value,
                                        indeks = indeks.value
                                    ))
                                }) {
                                    Text(text = "Ekran C")
                                }
                            }

                        }
                    }
                    composable<EkranB> {
                        val args = it.toRoute<EkranB>()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Red),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Imie: ${args.imie}, numer indeksu: ${args.indeks}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            val imie = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = imie.value,
                                onValueChange = {imie.value = it},
                                placeholder = { Text(text = "Imie:")}
                                //label = {Text(text = "Imie:") }
                            )

                            val indeks = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = indeks.value,
                                onValueChange = { if(it.isDigitsOnly()) indeks.value = it},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                placeholder = {Text(text = "Numer indeksu:")})

                            Row{
                                Button(
                                    modifier = Modifier.padding(end = 25.dp),
                                    onClick = {
                                        navController.navigate(EkranA(
                                            imie = imie.value,
                                            indeks = indeks.value
                                        ))
                                    }) {
                                    Text(
                                        color = Color.Black,
                                        text = "Ekran A"
                                    )
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                    onClick = {
                                        navController.navigate(EkranC(
                                            imie = imie.value,
                                            indeks = indeks.value
                                        ))
                                    }) {
                                    Text(text = "Ekran C")
                                }
                            }

                        }
                    }

                    composable<EkranC> {
                        val args = it.toRoute<EkranB>()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.Black),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Imie: ${args.imie}, numer indeksu: ${args.indeks}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            val imie = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = imie.value,
                                onValueChange = {imie.value = it},
                                placeholder = { Text(text = "Imie:")}
                                //label = {Text(text = "Imie:") }
                            )

                            val indeks = remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(bottom = 5.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedContainerColor = Color.LightGray,
                                    focusedContainerColor = Color.LightGray,
                                    disabledContainerColor = Color.LightGray,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                value = indeks.value,
                                onValueChange = { if(it.isDigitsOnly()) indeks.value = it},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                placeholder = {Text(text = "Numer indeksu:")})

                            Row{
                                Button(
                                    modifier = Modifier.padding(end = 25.dp),
                                    onClick = {
                                        navController.navigate(EkranA(
                                            imie = imie.value,
                                            indeks = indeks.value
                                        ))
                                    }) {
                                    Text(
                                        color = Color.Black,
                                        text = "Ekran A"
                                    )
                                }
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    modifier = Modifier.padding(end = 25.dp),
                                    onClick = {
                                        navController.navigate(EkranB(
                                            imie = imie.value,
                                            indeks = indeks.value
                                        ))
                                    }) {
                                    Text(
                                        color = Color.Black,
                                        text = "Ekran B"
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Serializable
data class EkranA(
    val imie: String?,
    val indeks: String?
)
@Serializable
data class EkranB(
    val imie: String?,
    val indeks: String?
)

@Serializable
data class EkranC(
    val imie: String?,
    val indeks: String?
)