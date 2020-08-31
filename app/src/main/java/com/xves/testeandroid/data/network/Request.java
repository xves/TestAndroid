package com.xves.testeandroid.data.network;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.xves.testeandroid.ui.activities.MainActivity;
import com.xves.testeandroid.data.Cupons;
import com.xves.testeandroid.data.Events;
import com.xves.testeandroid.data.People;

public class Request {

    public static void getEvents(Activity activity) {
        String URL = APIManager.Events;
        DataBase base = new DataBase(activity);

        Ion.with(activity)
                .load(URL)
                .asJsonArray()
                .setCallback((e, result) -> {
                    try {
                        for (int i = 0; i < result.size(); i++) {
                            JsonObject obj = result.get(i).getAsJsonObject();

                            JsonArray people = obj.getAsJsonArray("people");
                            SQLiteDatabase db = base.getWritableDatabase();
                            for (int j = 0; j < people.size(); j++) {
                                JsonObject c = people.get(j).getAsJsonObject();
                                People p = new People();
                                p.setId(c.get("id").getAsInt());
                                p.setEventId(c.get("eventId").getAsInt());
                                p.setName(c.get("name").getAsString());
                                p.setPicture(c.get("picture").getAsString());

                                ContentValues val = new ContentValues();
                                val.put("id", p.getId());
                                val.put("eventId", p.getEventId());
                                val.put("name", p.getName());
                                val.put("picture", p.getPicture());

                                db.insertWithOnConflict("people", null, val, SQLiteDatabase.CONFLICT_REPLACE);
                            }

                            JsonArray cupons = obj.getAsJsonArray("cupons");
                            for (int q = 0; q < cupons.size(); q++) {
                                JsonObject c = cupons.get(q).getAsJsonObject();
                                Cupons cup = new Cupons();
                                cup.setId(c.get("id").getAsInt());
                                cup.setEventId(c.get("eventId").getAsInt());
                                cup.setDiscount(c.get("discount").getAsString());

                                ContentValues val = new ContentValues();
                                val.put("id", cup.getId());
                                val.put("eventId", cup.getEventId());
                                val.put("discount", cup.getDiscount());

                                db.insertWithOnConflict("cupons", null, val, SQLiteDatabase.CONFLICT_REPLACE);
                            }

                            Events ev = new Events();
                            ev.setDate(obj.get("date").getAsString());
                            ev.setDescrition(obj.get("description").getAsString());
                            ev.setImage(obj.get("image").getAsString());
                            ev.setLongitude(obj.get("longitude").getAsString());
                            ev.setLatitude(obj.get("latitude").getAsString());
                            ev.setPrice(obj.get("price").getAsDouble());
                            ev.setTitle(obj.get("title").getAsString());
                            ev.setId(obj.get("id").getAsInt());

                            Double pric = obj.get("price").getAsDouble();

                            ContentValues v = new ContentValues();
                            v.put("date", obj.get("date").getAsString());
                            v.put("description", obj.get("description").getAsString());
                            v.put("image", obj.get("image").getAsString());
                            v.put("longitude", obj.get("longitude").getAsString());
                            v.put("latitude", obj.get("latitude").getAsString());
                            v.put("price", obj.get("price").getAsDouble());
                            v.put("title", obj.get("title").getAsString());
                            v.put("id", obj.get("id").getAsInt());

                            db.insertWithOnConflict("events", null, v, SQLiteDatabase.CONFLICT_REPLACE);

                        }

                        activity.startActivity(new Intent(activity, MainActivity.class));

                    } catch (Exception erro) {
                        Toast.makeText(activity, "Ocorreu um erro! Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public static void Checkin(Activity activity, String eventid, String name, String email) {
        String URL = APIManager.Chekin;

        Ion.with(activity)
                .load(URL)
                .setBodyParameter("eventid", eventid)
                .setBodyParameter("name", name)
                .setBodyParameter("email", email)
                .asJsonObject()
                .setCallback((e, result) -> {
                    try {
                        String res = result.get("code").getAsString();

                        if (res.equals("erro")) {
                            Toast.makeText(activity, "Erro ao atualizar item.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "Produto atualizado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception erro) {
                        Toast.makeText(activity, "Ocorreu um erro! Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                    }

                });
    }


}
