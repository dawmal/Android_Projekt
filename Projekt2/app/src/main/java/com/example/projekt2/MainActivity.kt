package com.example.projekt2

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.health.connect.datatypes.PowerRecord
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekt2.ui.theme.Projekt2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import android.os.PowerManager
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var lightSensor: Sensor? = null
    private var lux = MutableStateFlow<Float>(0f)
    private var tlo = MutableStateFlow(Color.DarkGray)
    private var tekst = MutableStateFlow(Color.White)

    private var proximitySensor: Sensor? = null
    private var dis = MutableStateFlow<Boolean>(false)

    private lateinit var powerManager: PowerManager
    private lateinit var lock: PowerManager.WakeLock


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        lock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "simplewakelock:wakelocktag")

        setContent {

            Projekt2Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = EkranA
                ){
                    composable<EkranA>{
                        val lux = lux.collectAsState()
                        val tekst = tekst.collectAsState()

                        if(lux.value > 1000){
                            window.attributes = window.attributes.apply {
                                screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL
                            }
                        } else {
                            window.attributes = window.attributes.apply {
                                screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = tlo.value),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Luxy: ${lux.value}",
                                color = tekst.value
                            )
                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = tekst.value),
                                onClick = {
                                navController.navigate(EkranB)
                            }) {
                                Text(
                                    color = tlo.value,
                                    text = "Czujnik odległości"
                                )
                            }
                        }
                    }
                    composable<EkranB>{
                        val dis = dis.collectAsState()

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Odległość: ${dis.value}")
                            if(!dis.value){
                                if(lock.isHeld) lock.release()
                            } else {
                                if(!lock.isHeld) lock.acquire()
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                onClick = {
                                    navController.navigate(EkranA)
                                }) {
                                Text(
                                    color = Color.White,
                                    text = "Czujnik światła"
                                )
                            }
                        }
                    }
                }
            }
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        sensorEvent?.let { event ->
            if(event.sensor.type==Sensor.TYPE_LIGHT){
                lux.update { event.values[0] }
                if(lux.value < 60){
                    tlo.value = Color.DarkGray
                    tekst.value = Color.White
                } else {
                    tlo.value = Color.White
                    tekst.value = Color.Black
                }
            }
            if(event.sensor.type==Sensor.TYPE_PROXIMITY){
                if(event.values[0] > 1){
                    dis.update { false }
                } else {
                    dis.update { true }
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onResume() {
        super.onResume()
        lightSensor?.also { light ->
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
        proximitySensor?.also { distance ->
            sensorManager.registerListener(this, distance, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


}



@Serializable
object EkranA

@Serializable
object EkranB