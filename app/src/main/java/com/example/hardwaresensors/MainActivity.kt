package com.example.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.getSystemService
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor
    lateinit var accelSensor: Sensor
    lateinit var flProxIndicator: FrameLayout
    lateinit var flAccelIndicator: FrameLayout

    val colors = arrayOf(Color.RED , Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN)

    //this function is used to detect accuracy based things like difference in brightness
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent?) {
        flProxIndicator= findViewById(R.id.flProxIndicator)
        flAccelIndicator= findViewById(R.id.flProxIndicator)
//        if(event!!.values[0]>0){
//            flProxIndicator.setBackgroundColor(colors[Random.nextInt(5)])
//        }
        Log.d("HWSENS","""
            ----
            ax = ${event!!.values[0]}
            ay = ${event!!.values[1]}
            az = ${event!!.values[2]}
            ----
        """.trimIndent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sensorManager = getSystemService<SensorManager>()!!
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

    }

    //lists the values of proximity sensor only when app is open
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelSensor,1000*1000)
    }

    //this pauses the sensor work when app is closed or screen is turned off
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}