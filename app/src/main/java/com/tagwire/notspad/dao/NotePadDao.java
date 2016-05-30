package com.tagwire.notspad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jing on 16/4/23.
 * 记事本的数据处理模块
 */
public class NotePadDao {
    private NotepadDBHelper dbHelper;
    public NotePadDao(Context context) {
        dbHelper = new NotepadDBHelper(context,"notepad.db",null,1);
    }
    private static final String TABLE = "notetab";
    //添加新的记录
    public long insert(ContentValues values){
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        long id = sdb.insert(TABLE,null,values);
        sdb.close();
        return id;
    }
    //查询
    public Cursor query(){
        String sql =  "select * from notetab " +
                "order by _created desc";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    //根据id查询
    public Cursor queryById(int id){
        String sql = "select * from notetab "+
                "where _id=?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.rawQuery(sql,new String[]{String.valueOf(id)});
    }
    //删除记录
    public void delete(String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE,"_id=?",new String[]{id});
        db.close();
    }
    //更新记录
    public void update(ContentValues values,String whereClause,String[] whereArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(TABLE,values,whereClause,whereArgs);
        db.close();
    }

    /**
     * 记事本数据库帮助类
     * @author tagwire
     */
    private class NotepadDBHelper extends SQLiteOpenHelper{

        public NotepadDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql=
                    "create table if not exists notetab(" +
                            "_id integer primary key autoincrement," +
                            "_content text not null," +
                            "_created text not null)";
            db.execSQL(sql);
            Log.i("TAG", "table create ok");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql="drop table if exists notetab";
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
