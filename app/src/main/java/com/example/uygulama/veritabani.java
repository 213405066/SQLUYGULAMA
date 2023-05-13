package com.example.uygulama;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class veritabani extends SQLiteOpenHelper {
    private static  final String VERİTABANİ_ADİ="ögrenciler";
    private static final  int SURUM=1;

    public veritabani (Context c)
    {
        super(c,VERİTABANİ_ADİ, null,SURUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ogrencibilgi (ad Text, soyad Text,yas Integer,sehir Text);");

    }
    public void onUpgrade (SQLiteDatabase db,int eskiversiyon,int yeniversion){
        db.execSQL("DROP TABLE IF EXISTS ogrencibilgi");
        onCreate(db);
    }
}
