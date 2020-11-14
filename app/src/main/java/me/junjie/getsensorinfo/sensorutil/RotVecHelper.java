package me.junjie.getsensorinfo.sensorutil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import me.junjie.getsensorinfo.StaticVar;

public class RotVecHelper implements SensorEventListener {
    private SensorManager sensorManager =
            (SensorManager) StaticVar.mainContext.getSystemService(Context.SENSOR_SERVICE);
    private Sensor rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    public void registListener(){
        if(rotSensor == null){
            Toast.makeText(StaticVar.mainContext, "你的设备不支持旋转传感器", Toast.LENGTH_LONG).show();
        }
        sensorManager.registerListener(this, rotSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregistListener(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Message msg = Message.obtain();
        msg.obj = "ROT_MSG";
        Bundle bundle = new Bundle();
        bundle.putFloatArray("rot_value", event.values);
        bundle.putLong("rot_timestamp", event.timestamp);
        msg.setData(bundle);
        StaticVar.infoHandler.sendMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
