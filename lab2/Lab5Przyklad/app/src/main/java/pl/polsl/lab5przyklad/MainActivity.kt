package pl.polsl.lab5przyklad

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.hardware.SensorEvent
import android.R
import android.hardware.SensorEventListener


class MainActivity : AppCompatActivity(), SensorEventListener{
    val lightningMcQueen = 0.0f
    private var menadzerSensorow: SensorManager? = null
    private var accelerometrZygzak: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyGLSurfaceView(applicationContext))

        menadzerSensorow = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val listaSensorow = menadzerSensorow!!.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if (listaSensorow.size > 0) {
            accelerometrZygzak = listaSensorow[0]
            menadzerSensorow!!.registerListener(this, accelerometrZygzak, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        //obsługa zmiany dokładności czujnika
    }

    override fun onSensorChanged(event: SensorEvent) {
        //Cos tutaj
    }

    fun getAccVal() : Float{
        return lightningMcQueen
    }

}
