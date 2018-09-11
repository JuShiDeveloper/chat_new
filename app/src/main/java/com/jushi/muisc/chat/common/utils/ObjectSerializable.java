package com.jushi.muisc.chat.common.utils;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Created by cpx on 2018/1/27.
 */

public class ObjectSerializable {
    private Activity activity;

    public ObjectSerializable(Activity activity) {
        this.activity = activity;
    }

    public void toWrite(Object obj, Class<?> cls) {
        File file = new File(activity.getCacheDir(), cls.toString());
        ObjectOutputStream oos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object toRead(Class<?> cls){
        File file = new File(activity.getCacheDir(),cls.toString());
        ObjectInputStream ois = null;
        if (file.exists()){
            try{
                ois = new ObjectInputStream(new FileInputStream(file));
                return ois.readObject();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (ois != null)
                        ois.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
