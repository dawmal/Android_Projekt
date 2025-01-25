package com.example.paragon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Category(modifier: Modifier = Modifier, navController: NavController){
    var nameIndex by remember { mutableIntStateOf(0) }
    var category by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    LaunchedEffect(nameIndex) {
        if(nameIndex <= name.count() - 1){
            if(checkG[nameIndex] == false ){
                if(nameIndex != name.count() - 1){
                    nameIndex++
                } else {
                    navController.navigate("Start")
                }
            }
        } else {
            navController.navigate("Start")
        }
    }

    val currentName = name[nameIndex]

    val savedCategory = readData(context, currentName)
    LaunchedEffect(savedCategory) {
        if(savedCategory != null){
            category = TextFieldValue(savedCategory)
        } else {
            category = TextFieldValue("")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\nDo jakiej kategorii dodać: \n $currentName",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Wpisz kategorię") },
            modifier = modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                if(category.text.isNotEmpty()){
                    saveData(context, currentName, category.text.trim())
                    if (nameIndex <= name.count() - 1){
                        if(nameIndex != name.count() - 1){
                            nameIndex++
                        } else {
                            navController.navigate("Start")
                        }
                    }
                }
            }
        ) {
            Text("Przypisz kategorię" )
        }
    }
}