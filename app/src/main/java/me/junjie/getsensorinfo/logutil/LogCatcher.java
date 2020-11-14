package me.junjie.getsensorinfo.logutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class LogCatcher {

    private boolean status = false;
    private String absPath = null;
    private Context context;
    private static final String TAG = "LogCatcher";
    private FileOutputStream fileWriter;
    public LogCatcher(Context context, String fileName) {
        this.context = context;
        File file = getFileDir(fileName);
        this.absPath = file.getAbsolutePath();
        Log.d(TAG, "LogCatcher: "+absPath);
        try{
            fileWriter = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.d(TAG, "LogCatcher: file not found!");
        }

    }

    private File getFileDir(String fileDirname){
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), fileDirname);
        return file;
    }

    public void write(float[] data, long timestamp){
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp);
        for(float d : data){
            sb.append(" ");
            String temp = String.valueOf(d);
            sb.append(temp);
        }
        sb.append("\n");
        if(status){
            try{
                fileWriter.write(sb.toString().getBytes());
            }catch (IOException e){
                e.printStackTrace();
                Log.d(TAG, "write: io wrong");
            }
        }
    }

    public void setStatus(boolean status){
        this.status = status;
    }
}
