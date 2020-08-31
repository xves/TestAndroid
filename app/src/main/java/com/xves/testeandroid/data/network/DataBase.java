package com.xves.testeandroid.data.network;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "teste", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE people (                         "+
                     "             id INTEGER NOT NULL PRIMARY KEY, "+
                     "             eventId INTEGER,                 "+
                     "             name TEXT,                       "+
                     "             picture TEXT);";

        db.execSQL(sql);

        sql = "CREATE TABLE cupons(                         "+
              "           id INTEGER NOT NULL PRIMARY KEY,  "+
              "           eventId INTEGER,                  "+
              "           discount TEXT);";

        db.execSQL(sql);

        sql = "CREATE TABLE events(                           "+
              "             id INTEGER NOT NULL PRIMARY KEY,  "+
              "             title TEXT,                       "+
              "             image TEXT,                       "+
              "             description TEXT,                 "+
              "             longitude TEXT,                   "+
              "             latitude TEXT,                    "+
              "             price Double,                     "+
              "             date TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS people;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS cupons;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS events;";
        db.execSQL(sql);

        onCreate(db);
    }

}