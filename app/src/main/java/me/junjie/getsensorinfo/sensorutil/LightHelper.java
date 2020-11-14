package me.junjie.getsensorinfo.sensorutil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;

import me.junjie.getsensorinfo.StaticVar;

public class LightHelper implements SensorEventListener {
    private SensorManager sensorManager =
            (SensorManager) StaticVar.mainContext.getSystemService(Context.SENSOR_SERVICE);
    private Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    public void registListener(){
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregistListener(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Message msg = Message.obtain();
        msg.obj = "LIG_MSG";
        Bundle bundle = new Bundle();
        bundle.putFloatArray("lig_value", event.values);
        bundle.putLong("lig_timestamp", event.timestamp);
        msg.setData(bundle);
        StaticVar.infoHandler.sendMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
