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
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor
    lateinit var accelSensor: Sensor
    lateinit var flProxIndicator: FrameLayout
    lateinit var flAccelIndicator: FrameLayout

    val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.DKGRAY)

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] > 0) {
                    flProxIndicator.setBackgroundColor(colors[Random.nextInt(7)])
                }
            } else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val bgColor = Color.rgb(
                    accel2color(event.values[0]),
                    accel2color(event.values[1]),
                    accel2color(event.values[2])
                )
                flAccelIndicator.setBackgroundColor(bgColor)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flProxIndicator = findViewById(R.id.flProxIndicator)
        flAccelIndicator = findViewById(R.id.flAccelIndicator)

        sensorManager = getSystemService<SensorManager>()!!
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)!!
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun accel2color(accel: Float) = (((accel + 12) / 24) * 255).roundToInt()
}
