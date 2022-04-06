package com.example.ambientsensordemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ambientsensordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , SensorEventListener {

    private lateinit var binding:ActivityMainBinding

    private lateinit var mSensorManager:SensorManager
    private lateinit var mSensor: Sensor
    private var isAmbientSensor: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d("MainActivity","onCreate method")

        mSensorManager = getSystemService(Context.SENSOR_SERVICE)
                as SensorManager

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){

            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isAmbientSensor = true
        }
        else{
            binding.tvSensor.text = "Sensor not found on this device."
            isAmbientSensor = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAmbientSensor){
            mSensor.also {
                mSensorManager.registerListener(this , mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        if (isAmbientSensor){
            mSensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        binding.tvSensor.text = "Temperature =  ${event!!.values[0]} * C"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}