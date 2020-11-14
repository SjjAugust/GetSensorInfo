package me.junjie.getsensorinfo.uiutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.junjie.getsensorinfo.R;

public class MutiAdapter extends BaseAdapter {
    private static final int TYPE_GYRO = 0;
    private static final int TYPE_ACC = 1;
    private static final int TYPE_MAG = 2;
    private static final int TYPE_ROT = 3;
    private static final int TYPE_LIG = 4;
    private static final int TYPE_DIS = 5;
    private Context context;
    private ArrayList<Object> mData = null;
    private LayoutInflater layoutInflater;

    public MutiAdapter(Context context, ArrayList<Object> mDate){
        this.context = context;
        this.mData = mDate;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Object mTemp = mData.get(position);
        if(mTemp instanceof AllSensorInfo.GyroInfo){
            return TYPE_GYRO;
        }else if(mTemp instanceof AllSensorInfo.AccInfo){
            return TYPE_ACC;
        }else if(mTemp instanceof AllSensorInfo.MagInfo){
            return TYPE_MAG;
        }else if(mTemp instanceof AllSensorInfo.RotInfo){
            return TYPE_ROT;
        }else if(mTemp instanceof AllSensorInfo.LigInfo){
            return TYPE_LIG;
        }else if(mTemp instanceof AllSensorInfo.DisInfo){
            return TYPE_DIS;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderGyro viewHolderGyro = null;
        ViewHolderAcc viewHolderAcc = null;
        ViewHolderMag viewHolderMag = null;
        ViewHolderRot viewHolderRot = null;
        ViewHolderLig viewHolderLig = null;
        ViewHolderDis viewHolderDis = null;
        int type = getItemViewType(position);
        if(convertView == null){
            layoutInflater = LayoutInflater.from(context);
            switch (type){
                case TYPE_GYRO:
                    convertView = layoutInflater.inflate(R.layout.gyro_card, parent, false);
                    viewHolderGyro = new ViewHolderGyro();
                    viewHolderGyro.gyroPitch = (TextView)convertView.findViewById(R.id.txt_show_gyro_pitch);
                    viewHolderGyro.gyroRoll = (TextView)convertView.findViewById(R.id.txt_show_gyro_roll);
                    viewHolderGyro.gyroYaw = (TextView)convertView.findViewById(R.id.txt_show_gyro_yaw);
                    viewHolderGyro.gyroTime = (TextView)convertView.findViewById(R.id.txt_show_gyro_time);
                    viewHolderGyro.gyroName = (TextView)convertView.findViewById(R.id.txt_gyro_name);
                    convertView.setTag(viewHolderGyro);
                    break;
                case TYPE_ACC:
                    convertView = layoutInflater.inflate(R.layout.gyro_card, parent, false);
                    viewHolderAcc = new ViewHolderAcc();
                    viewHolderAcc.accX = (TextView)convertView.findViewById(R.id.txt_show_gyro_pitch);
                    viewHolderAcc.accY = (TextView)convertView.findViewById(R.id.txt_show_gyro_roll);
                    viewHolderAcc.accZ = (TextView)convertView.findViewById(R.id.txt_show_gyro_yaw);
                    viewHolderAcc.accTime= (TextView)convertView.findViewById(R.id.txt_show_gyro_time);
                    viewHolderAcc.accName = (TextView)convertView.findViewById(R.id.txt_gyro_name);
                    convertView.setTag(viewHolderAcc);
                    break;
                case TYPE_MAG:
                    convertView = layoutInflater.inflate(R.layout.gyro_card, parent, false);
                    viewHolderMag = new ViewHolderMag();
                    viewHolderMag.magX= (TextView)convertView.findViewById(R.id.txt_show_gyro_pitch);
                    viewHolderMag.magY = (TextView)convertView.findViewById(R.id.txt_show_gyro_roll);
                    viewHolderMag.magZ = (TextView)convertView.findViewById(R.id.txt_show_gyro_yaw);
                    viewHolderMag.magTime= (TextView)convertView.findViewById(R.id.txt_show_gyro_time);
                    viewHolderMag.magName = (TextView)convertView.findViewById(R.id.txt_gyro_name);
                    convertView.setTag(viewHolderMag);
                    break;
                case TYPE_ROT:
                    convertView = layoutInflater.inflate(R.layout.gyro_card, parent, false);
                    viewHolderRot = new ViewHolderRot();
                    viewHolderRot.rotX = (TextView)convertView.findViewById(R.id.txt_show_gyro_pitch);
                    viewHolderRot.rotY = (TextView)convertView.findViewById(R.id.txt_show_gyro_roll);
                    viewHolderRot.rotZ = (TextView)convertView.findViewById(R.id.txt_show_gyro_yaw);
                    viewHolderRot.rotTime= (TextView)convertView.findViewById(R.id.txt_show_gyro_time);
                    viewHolderRot.rotName = (TextView)convertView.findViewById(R.id.txt_gyro_name);
                    convertView.setTag(viewHolderRot);
                    break;
                case TYPE_LIG:
                    convertView = layoutInflater.inflate(R.layout.temp_card, parent, false);
                    viewHolderLig = new ViewHolderLig();
                    viewHolderLig.light = (TextView)convertView.findViewById(R.id.txt_show_temp);
                    viewHolderLig.ligTime = (TextView)convertView.findViewById(R.id.txt_show_temp_time);
                    viewHolderLig.ligName = (TextView)convertView.findViewById(R.id.txt_temp_name);
                    convertView.setTag(viewHolderLig);
                    break;
                case TYPE_DIS:
                    convertView = layoutInflater.inflate(R.layout.temp_card, parent, false);
                    viewHolderDis = new ViewHolderDis();
                    viewHolderDis.distance = (TextView)convertView.findViewById(R.id.txt_show_temp);
                    viewHolderDis.disTime = (TextView)convertView.findViewById(R.id.txt_show_temp_time);
                    viewHolderDis.disName = (TextView)convertView.findViewById(R.id.txt_temp_name);
                    convertView.setTag(viewHolderDis);
                    break;
            }
        }else{
            switch (type){
                case TYPE_GYRO:
                    viewHolderGyro = (ViewHolderGyro)convertView.getTag();
                    break;
                case TYPE_ACC:
                    viewHolderAcc = (ViewHolderAcc)convertView.getTag();
                    break;
                case TYPE_MAG:
                    viewHolderMag = (ViewHolderMag)convertView.getTag();
                    break;
                case TYPE_ROT:
                    viewHolderRot = (ViewHolderRot) convertView.getTag();
                    break;
                case TYPE_LIG:
                    viewHolderLig = (ViewHolderLig) convertView.getTag();
                    break;
                case TYPE_DIS:
                    viewHolderDis= (ViewHolderDis) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case TYPE_GYRO:
                AllSensorInfo.GyroInfo gyroInfo = (AllSensorInfo.GyroInfo)mData.get(position);
                viewHolderGyro.gyroPitch.setText(gyroInfo.pitchStr);
                viewHolderGyro.gyroRoll.setText(gyroInfo.rollStr);
                viewHolderGyro.gyroYaw.setText(gyroInfo.yawStr);
                viewHolderGyro.gyroTime.setText(gyroInfo.timeStr);
                viewHolderGyro.gyroName.setText(context.getString(R.string.gyro_name));
                break;
            case TYPE_ACC:
                AllSensorInfo.AccInfo accInfo = (AllSensorInfo.AccInfo)mData.get(position);
                viewHolderAcc.accX.setText(accInfo.xStr);
                viewHolderAcc.accY.setText(accInfo.yStr);
                viewHolderAcc.accZ.setText(accInfo.zStr);
                viewHolderAcc.accTime.setText(accInfo.timeStr);
                viewHolderAcc.accName.setText(context.getString(R.string.acc_name));
                break;
            case TYPE_MAG:
                AllSensorInfo.MagInfo magInfo = (AllSensorInfo.MagInfo)mData.get(position);
                viewHolderMag.magX.setText(magInfo.xStr);
                viewHolderMag.magY.setText(magInfo.yStr);
                viewHolderMag.magZ.setText(magInfo.zStr);
                viewHolderMag.magTime.setText(magInfo.timeStr);
                viewHolderMag.magName.setText("Magnetic Field");
                break;
            case TYPE_ROT:
                AllSensorInfo.RotInfo rotInfo = (AllSensorInfo.RotInfo)mData.get(position);
                viewHolderRot.rotX.setText(rotInfo.xStr);
                viewHolderRot.rotY.setText(rotInfo.yStr);
                viewHolderRot.rotZ.setText(rotInfo.zStr);
                viewHolderRot.rotTime.setText(rotInfo.timeStr);
                viewHolderRot.rotName.setText("Rotation Vector");
                break;
            case TYPE_LIG:
                AllSensorInfo.LigInfo ligInfo = (AllSensorInfo.LigInfo)mData.get(position);
                viewHolderLig.light.setText(ligInfo.ligStr);
                viewHolderLig.ligTime.setText(ligInfo.timeStr);
                viewHolderLig.ligName.setText("Light Intensity");
                break;
            case TYPE_DIS:
                AllSensorInfo.DisInfo disInfo = (AllSensorInfo.DisInfo)mData.get(position);
                viewHolderDis.distance.setText(disInfo.disStr);
                viewHolderDis.disTime.setText(disInfo.timeStr);
                viewHolderDis.disName.setText("Distance");
                break;
        }
        return convertView;
    }

    private static class ViewHolderGyro{
        TextView gyroPitch;
        TextView gyroRoll;
        TextView gyroYaw;
        TextView gyroTime;
        TextView gyroName;
    }
    private static class ViewHolderAcc{
        TextView accX;
        TextView accY;
        TextView accZ;
        TextView accTime;
        TextView accName;
    }
    private static class ViewHolderMag{
        TextView magX;
        TextView magY;
        TextView magZ;
        TextView magTime;
        TextView magName;
    }
    private static class ViewHolderRot{
        TextView rotX;
        TextView rotY;
        TextView rotZ;
        TextView rotTime;
        TextView rotName;
    }
    private static class ViewHolderLig{
        TextView light;
        TextView ligTime;
        TextView ligName;
    }
    private static class ViewHolderDis{
        TextView distance;
        TextView disTime;
        TextView disName;
    }
}
