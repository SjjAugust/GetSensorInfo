package me.junjie.getsensorinfo.sensorutil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;

import me.junjie.getsensorinfo.StaticVar;

public class MagneticHelper implements SensorEventListener {
    private SensorManager sensorManager =
            (SensorManager) StaticVar.mainContext.getSystemService(Context.SENSOR_SERVICE);
    private Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    public void registListener(){
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregistListener(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Message msg = Message.obtain();
        msg.obj = "MAG_MSG";
        Bundle bundle = new Bundle();
        bundle.putFloatArray("magnetic_value", event.values);
        bundle.putLong("magnetic_timestamp", event.timestamp);
        msg.setData(bundle);
        StaticVar.infoHandler.sendMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
