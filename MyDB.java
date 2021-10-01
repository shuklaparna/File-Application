package com.example.fileapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDB extends SQLiteOpenHelper {

    static final private String DB_name="Files";
    static final private String DB_table="Names";
    static final private int db_ver=1;
    Context ctx;
    SQLiteDatabase o;

    public MyDB(Context ct)
    {
        super(ct,DB_name,null,db_ver);
        ctx=ct;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DB_table+" (id varchar(20) primary key,Fname varchar(20) );");
        Log.i("DATABASE ","TABLE CREATED");
        Toast.makeText(ctx,"DataBase created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void set(String s)
    {
        String str=s+".txt";
        o=getWritableDatabase();
        o.execSQL("Insert into "+DB_table+" values('"+s+"','"+str+"');");
        Toast.makeText(ctx,"filename inserted into DB",Toast.LENGTH_SHORT).show();
    }

    public boolean check(String s1) {
        o = getReadableDatabase();
        String query="SELECT * FROM "+DB_table;
        Cursor c=o.rawQuery(query,null);
        ArrayList<String> a=new ArrayList<>();
        while(c.moveToNext())
        {
            String s=c.getString(0);
            a.add(s);
        }
        if(a.contains(s1))
        {
            return false;
        }
        return true;
    }
}