package me.junjie.getsensorinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.junjie.getsensorinfo.logutil.LogCatcher;
import me.junjie.getsensorinfo.sensorutil.AccHelper;
import me.junjie.getsensorinfo.sensorutil.DistanceHelper;
import me.junjie.getsensorinfo.sensorutil.GyroHelper;
import me.junjie.getsensorinfo.sensorutil.LightHelper;
import me.junjie.getsensorinfo.sensorutil.MagneticHelper;
import me.junjie.getsensorinfo.sensorutil.RotVecHelper;
import me.junjie.getsensorinfo.uiutil.AllSensorInfo;
import me.junjie.getsensorinfo.uiutil.MutiAdapter;

public class MainActivity extends AppCompatActivity {

    private AllSensorInfo allSensorInfo = new AllSensorInfo();
    private GyroHelper gyroHelper;
    private AccHelper accHelper;
    private MagneticHelper magneticHelper;
    private RotVecHelper rotVecHelper;
    private LightHelper lightHelper;
    private DistanceHelper distanceHelper;
    private LogCatcher gyroLog;
    private LogCatcher accLog;
    private LogCatcher magLog;
    private LogCatcher rotLog;
    private LogCatcher ligLog;
    private LogCatcher disLog;

    private static final String TAG = "MainActivity";
    private ListView listView;
    private ArrayList<Object> mData = new ArrayList<>();
    private MutiAdapter mutiAdapter;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Button btnStartWrite;
    private Button btnStopWrite;
    private Button btnShowInfo;

    private void init(){
        StaticVar.mainContext = this;
        gyroLog = new LogCatcher(this, "gyro.txt");
        accLog = new LogCatcher(this, "acc.txt");
        magLog = new LogCatcher(this, "mag.txt");
        rotLog = new LogCatcher(this, "rot.txt");
        ligLog = new LogCatcher(this, "lig.txt");
        disLog = new LogCatcher(this, "dis.txt");
        StaticVar.infoHandler = new InfoHandler();

        mData.add(new AllSensorInfo.GyroInfo());
        mData.add(new AllSensorInfo.AccInfo());
        mData.add(new AllSensorInfo.MagInfo());
        mData.add(new AllSensorInfo.RotInfo());
        mData.add(new AllSensorInfo.LigInfo());
        mData.add(new AllSensorInfo.DisInfo());
        mutiAdapter = new MutiAdapter(this, mData);
        listView.setAdapter(mutiAdapter);


        gyroHelper = new GyroHelper();
        gyroHelper.registListener();
        accHelper = new AccHelper();
        accHelper.registListener();
        magneticHelper = new MagneticHelper();
        magneticHelper.registListener();
        rotVecHelper = new RotVecHelper();
        rotVecHelper.registListener();
        lightHelper = new LightHelper();
        lightHelper.registListener();
        distanceHelper = new DistanceHelper();
        distanceHelper.registListener();

    }

    private void setWidgt(){
        btnStartWrite = findViewById(R.id.btn_start_save_log);
        btnStopWrite = findViewById(R.id.btn_stop_save_log);
        btnShowInfo = findViewById(R.id.btn_show_sensor);

        btnStartWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWriteLog();
                Toast.makeText(StaticVar.mainContext, "开始写入log", Toast.LENGTH_LONG).show();
            }
        });
        btnStopWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWriteLog();
                Toast.makeText(StaticVar.mainContext, "停止写入log", Toast.LENGTH_LONG).show();
            }
        });
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
                StringBuilder sb = new StringBuilder();
                for (Sensor sensor : sensorList){
                    sb.append(sensor.getName()).append("\n");
                }
                Toast.makeText(StaticVar.mainContext, sb.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class InfoHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            processMessage(msg);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.main_list);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 0);
        }

        init();
        setWidgt();

    }

    private void processMessage(Message msg){
        switch (StaticVar.InfoId.getByName((String)msg.obj)){
            case GYRO_MSG:
                float[] speedGyro = msg.getData().getFloatArray("gyro_value");
                long timestampGyro = msg.getData().getLong("gyro_timestamp");
                gyroLog.write(speedGyro, timestampGyro);
                String pitchStrGyro = getString(R.string.gyro_pitch)+String.valueOf(speedGyro[0]);
                String rollStrGyro = getString(R.string.gyro_roll)+String.valueOf(speedGyro[1]);
                String yawStrGyro = getString(R.string.gyro_yaw)+String.valueOf(speedGyro[2]);
                String timeStrGyro = getString(R.string.gyro_timestamp)+String.valueOf(timestampGyro);
                AllSensorInfo.GyroInfo tempGyro = new AllSensorInfo.GyroInfo();
                tempGyro.pitchStr = pitchStrGyro;
                tempGyro.rollStr = rollStrGyro;
                tempGyro.yawStr = yawStrGyro;
                tempGyro.timeStr = timeStrGyro;
                mData.set(StaticVar.InfoId.GYRO_MSG.getId(), tempGyro);
                mutiAdapter.notifyDataSetChanged();
                break;
            case ACC_MSG:
                float[] speedAcc = msg.getData().getFloatArray("acc_value");
                long timestampAcc = msg.getData().getLong("acc_timestamp");
                accLog.write(speedAcc, timestampAcc);
                String pitchStrAcc = getString(R.string.acc_x)+String.valueOf(speedAcc[0]);
                String rollStrAcc = getString(R.string.acc_y)+String.valueOf(speedAcc[1]);
                String yawStrAcc = getString(R.string.acc_z)+String.valueOf(speedAcc[2]);
                String timeStrAcc = getString(R.string.acc_timestamp)+String.valueOf(timestampAcc);
                AllSensorInfo.AccInfo tempAcc = new AllSensorInfo.AccInfo();
                tempAcc.xStr = pitchStrAcc;
                tempAcc.yStr = rollStrAcc;
                tempAcc.zStr = yawStrAcc;
                tempAcc.timeStr = timeStrAcc;
                mData.set(StaticVar.InfoId.ACC_MSG.getId(), tempAcc);
                mutiAdapter.notifyDataSetChanged();
                break;
            case MAG_MSG:
                float[] intensityMag = msg.getData().getFloatArray("magnetic_value");
                long timestampMag = msg.getData().getLong("magnetic_timestamp");
                magLog.write(intensityMag, timestampMag);
                String xStr = getString(R.string.magnetic_x)+String.valueOf(intensityMag[0]);
                String yStr = getString(R.string.magnetic_y)+String.valueOf(intensityMag[1]);
                String zStr = getString(R.string.magnetic_z)+String.valueOf(intensityMag[2]);
                String timeStrMag = getString(R.string.magnetic_timestamp)+String.valueOf(timestampMag);
                AllSensorInfo.MagInfo tempMag = new AllSensorInfo.MagInfo();
                tempMag.xStr = xStr;
                tempMag.yStr = yStr;
                tempMag.zStr = zStr;
                tempMag.timeStr = timeStrMag;
                mData.set(StaticVar.InfoId.MAG_MSG.getId(), tempMag);
                mutiAdapter.notifyDataSetChanged();
                break;
            case ROT_MSG:
                float[] intensityRot = msg.getData().getFloatArray("rot_value");
                long timestampRot = msg.getData().getLong("rot_timestamp");
                rotLog.write(intensityRot, timestampRot);
                String xStrRot = getString(R.string.rot_x)+String.valueOf(intensityRot[0]);
                String yStrRot = getString(R.string.rot_y)+String.valueOf(intensityRot[1]);
                String zStrRot = getString(R.string.rot_z)+String.valueOf(intensityRot[2]);
                String timeStrRot = getString(R.string.rot_timestamp)+String.valueOf(timestampRot);
                AllSensorInfo.RotInfo tempRot = new AllSensorInfo.RotInfo();
                tempRot.xStr = xStrRot;
                tempRot.yStr = yStrRot;
                tempRot.zStr = zStrRot;
                tempRot.timeStr = timeStrRot;
                mData.set(StaticVar.InfoId.ROT_MSG.getId(), tempRot);
                mutiAdapter.notifyDataSetChanged();
                break;
            case LIG_MSG:
                float[] intensityLig = msg.getData().getFloatArray("lig_value");
                long timestampLig = msg.getData().getLong("lig_timestamp");
                ligLog.write(intensityLig, timestampLig);
                String ligStr = getString(R.string.lig_val)+String.valueOf(intensityLig[0]);
                String timeStrlig = getString(R.string.lig_timestamp)+String.valueOf(timestampLig);
                AllSensorInfo.LigInfo tempLig = new AllSensorInfo.LigInfo();
                tempLig.ligStr = ligStr;
                tempLig.timeStr = timeStrlig;
                mData.set(StaticVar.InfoId.LIG_MSG.getId(), tempLig);
                mutiAdapter.notifyDataSetChanged();
                break;
            case DIS_MSG:
                float[] intensityDis = msg.getData().getFloatArray("dis_value");
                long timestampDis = msg.getData().getLong("dis_timestamp");
                disLog.write(intensityDis, timestampDis);
                String disStr = getString(R.string.dis_val)+String.valueOf(intensityDis[0]);
                String timeStrdis = getString(R.string.dis_timestamp)+String.valueOf(timestampDis);
                AllSensorInfo.DisInfo tempDis = new AllSensorInfo.DisInfo();
                tempDis.disStr = disStr;
                tempDis.timeStr = timeStrdis;
                mData.set(StaticVar.InfoId.DIS_MSG.getId(), tempDis);
                mutiAdapter.notifyDataSetChanged();
                break;

        }
    }

    private void startWriteLog(){
        gyroLog.setStatus(true);
        accLog.setStatus(true);
        magLog.setStatus(true);
        rotLog.setStatus(true);
        ligLog.setStatus(true);
        disLog.setStatus(true);
    }
    private void stopWriteLog(){
        gyroLog.setStatus(false);
        accLog.setStatus(false);
        magLog.setStatus(false);
        rotLog.setStatus(false);
        ligLog.setStatus(false);
        disLog.setStatus(false);
    }
}