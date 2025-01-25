package com.example.projekt3

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun Home(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    var index by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var imie by remember {
        mutableStateOf("")
    }
    var indeks by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val db = Firebase.firestore
        Text(
            text = "Zalogowano",
            fontSize = 30.sp)
        db.collection("students")
            .get()
            .addOnSuccessListener {
                imie = it.documents[0].data?.get("name").toString()
                indeks = it.documents[0].data?.get("index").toString()
            }
        Text(text = "Imie: $imie, Numer indeksu: $indeks")
        OutlinedTextField(
            value = index,
            onValueChange = {index = it},
            label = {
                Text(text = "Indeks")
            }
        )
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {
                Text(text = "Imie")
            }
        )
        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                val student = hashMapOf(
                    "index" to "$index",
                    "name" to "$name"
                )
                
                db.collection("students")
                    .add(student)
            }) {
            Text(text = "Dodaj")
        }
        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                authViewModel.signout()
            }) {
            Text(text = "Wyloguj")
        }
    }
}