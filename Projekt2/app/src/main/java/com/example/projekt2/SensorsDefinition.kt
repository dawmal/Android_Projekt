package com.example.projekt2

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class LightSensor(
    context: Context
): SensorType(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)