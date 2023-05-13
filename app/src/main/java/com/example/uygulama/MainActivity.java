package com.example.uygulama;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button kaydet;
    Button goster;
    Button sil;
    Button guncelle;
    EditText ad;
    EditText soyad;
    EditText yas;
    EditText sehir;
    TextView bilgiler;

    private veritabani v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = new veritabani(this);

        kaydet=(Button) findViewById(R.id.buttonkayit);
       goster=(Button) findViewById(R.id.buttongoster);
        sil=(Button) findViewById(R.id.buttonsil);
        guncelle=(Button) findViewById(R.id.buttonguncelle);
        ad=(EditText) findViewById(R.id.editTextad);
        soyad=(EditText) findViewById(R.id.editTextsoyad);
        yas=(EditText) findViewById(R.id.editTextyas);
        sehir=(EditText) findViewById(R.id.editTextsehir);
        bilgiler=(TextView) findViewById(R.id.textViewbilgiler);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    kayitekle(ad.getText().toString(),soyad.getText().toString(),yas.getText().toString(),sehir.getText().toString());
                }
                finally {
                    v1.close();
                }
            }
        });

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=kayitgetir();
                kayitgoster(cursor);
            }
        });
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silme(ad.getText().toString());
            }
        });
       guncelle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               guncelle(ad.getText().toString(),soyad.getText().toString(),yas.getText().toString(),sehir.getText().toString());
           }
       });
    }
    private void silme(String adi){
        SQLiteDatabase db=v1.getReadableDatabase();
        db.delete("ogrencibilgi","ad"+"=?",new String[]{adi});

    }




    private String[] sutunlar={"ad","soyad","yas","sehir"};
    private Cursor kayitgetir(){
        SQLiteDatabase db=v1.getWritableDatabase();
        Cursor okunanlar = db.query("ogrencibilgi",sutunlar,null,null,null,null,null);
        return okunanlar;
    }

    private void kayitgoster(Cursor goster){
        StringBuilder builder=new StringBuilder();
        while (goster.moveToNext()){
            @SuppressLint("Range") String add=goster.getString(goster.getColumnIndex("ad"));
            @SuppressLint("Range") String soyadd=goster.getString(goster.getColumnIndex("soyad"));
            @SuppressLint("Range") String sehirr=goster.getString(goster.getColumnIndex("sehir"));
            @SuppressLint("Range") String yass=goster.getString(goster.getColumnIndex("yas"));
            builder.append("ad: ").append(add+"\n");
            builder.append("soyad: ").append(soyadd+"\n");
            builder.append("sehir: ").append(sehirr+"\n");
            builder.append("yas: ").append(yass+"\n");
            builder.append("-------------").append("\n");

        }
        TextView text=(TextView) findViewById(R.id.textViewbilgiler);
        text.setText(builder);


    }
    public void guncelle (String ad, String soyad,String yas,String sehir){
        SQLiteDatabase db=v1.getWritableDatabase();
        ContentValues cvguncelle=new ContentValues();
        cvguncelle.put("ad",ad);
        cvguncelle.put("soyad",soyad);
        cvguncelle.put("yas",yas);
        cvguncelle.put("sehir",sehir);

        db.close();
    }


    private void kayitekle(String adi, String soyadi ,String yasi, String sehri){
        SQLiteDatabase db=v1.getWritableDatabase();
        ContentValues veriler=new ContentValues();
        veriler.put("ad",adi);
        veriler.put("soyad",soyadi);
        veriler.put("yas",yasi);
        veriler.put("sehir",sehri);

        db.insertOrThrow("ogrencibilgi",null,veriler);




    }
}