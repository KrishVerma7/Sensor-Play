package com.example.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor

    //this function is used to detect accuracy based things like difference in brightness
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("HWSENS", """
                     onSensorChanged: ${event!!.values[0]}
                 """.trimIndent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService<SensorManager>()!!
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
    }

    //lists the values of proximity sensor only when app is open
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, proxSensor,1000*1000)
    }

    //this pauses the sensor work when app is closed or screen is turned off
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}