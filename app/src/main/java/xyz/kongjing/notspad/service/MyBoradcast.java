package xyz.kongjing.notspad.service;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import xyz.kongjing.notspad.dao.NotePadDao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jing on 16/7/28.
 */
public class MyBoradcast extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.facedetect.pickup")){
            String content = intent.getStringExtra("phone");
            NotePadDao dao=new NotePadDao(context);//context
            ContentValues values=new ContentValues();
            values.put("_content", content);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            values.put("_created", sdf.format(new Date()));
            long id=dao.insert(values);
            if(id!=-1){
                Intent intent1 = new Intent("com.facedetect.DETECT");
                intent1.putExtra("get","1");
                context.sendBroadcast(intent1);
            }
        }
    }
}
