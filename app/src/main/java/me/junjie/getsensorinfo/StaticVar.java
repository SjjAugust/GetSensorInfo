package me.junjie.getsensorinfo;

import android.content.Context;

import java.util.logging.Handler;

public class StaticVar {
    public static Context mainContext;
    public static MainActivity.InfoHandler infoHandler;

    public enum InfoId {
        GYRO_MSG(0),
        ACC_MSG(1),
        MAG_MSG(2),
        ROT_MSG(3),
        LIG_MSG(4),
        DIS_MSG(5);

        private int id = 0;
        private InfoId(int id){
            this.id = id;
        }
        public int getId(){
            return id;
        }

        public static InfoId getByName(String name){
            for(InfoId infoId : InfoId.values()){
                if(infoId.name().equalsIgnoreCase(name)){
                    return infoId;
                }
            }
            return null;
        }
    }
}
