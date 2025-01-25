package com.example.paragon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


@Composable
fun Start(modifier: Modifier = Modifier, navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var image by remember {
            mutableStateOf<Uri?>(null)
        }
        var text by remember {
            mutableStateOf("")
        }


        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                image = uri
            }
        )


        Button(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {
                text = ""
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            }) {
            Text("Wybierz zdjęcie")
        }

        AsyncImage(
            model = image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
        )

        val context = LocalContext.current
        var recognizedText: Text? = null

        if (image != null){
            Button(
                onClick = {
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    val imageToText = InputImage.fromFilePath(context, image!!)
                    recognizer.process(imageToText)
                        .addOnSuccessListener { visionText ->
                            recognizedText = visionText
                        }
                        .addOnCompleteListener {
                            text = textProcessing(recognizedText)
                        }

                }) {
                Text("Pobierz tekst")
            }
        }

        Text(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            text = text
        )
        if(text != ""){
            Button(onClick = {
                navController.navigate("TextOutput")
            }) {
                Text("Dalej")
            }
        }
        Spacer(modifier = modifier.height(5.dp))
    }
}

