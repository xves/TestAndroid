package com.xves.testeandroid.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.xves.testeandroid.data.network.DataBase;
import com.xves.testeandroid.R;
import com.xves.testeandroid.data.Events;
import com.xves.testeandroid.ui.EventsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    DataBase base;
    List<Events> datamodel;
    RecyclerView recyclerView;
    EventsAdapter recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base = new DataBase(this);
        prefs = getSharedPreferences("PREFUSUARIO", 0);

        datamodel = new ArrayList<>();
        recyclerView = findViewById(R.id.list_events);
        datamodel = getEvents();
        recycler = new EventsAdapter(datamodel, this);

        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);
        recyclerView.setItemViewCacheSize(datamodel.size());

    }

    public List<Events> getEvents() {
        List<Events> data = new ArrayList<>();
        SQLiteDatabase db = base.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT E.id,                    " +
                                    "       E.title,                 " +
                                    "       E.description,           " +
                                    "       E.image,                 " +
                                    "       E.price,                 " +
                                    "       E.longitude,             " +
                                    "       E.latitude,              " +
                                    "       E.date,                  " +
                                    "       P.name,                  " +
                                    "       P.picture,               " +
                                    "       C.discount               " +
                                    "  FROM events E,                " +
                                    "       people P,                " +
                                    "       cupons C                 " +
                                    " WHERE E.id = P.eventId         " +
                                    "   AND E.id = C.eventId         " +
                                    " ORDER BY E.id ASC", null);
        StringBuffer stringBuffer = new StringBuffer();
        Events dataModel = null;
        while (c.moveToNext()) {
            dataModel = new Events();
            int id = c.getInt(0);
            String title = c.getString(1);
            String description = c.getString(2);
            String image = c.getString(3);
            double price = c.getDouble(4);
            String longitude = c.getString(5);
            String latitude = c.getString(6);
            String date = c.getString(7);

            String name = c.getString(8);
            String picture = c.getString(9);
            String discount = c.getString(10);

            dataModel.setId(id);
            dataModel.setTitle(title);
            dataModel.setDescrition(description);
            dataModel.setImage(image);
            dataModel.setPrice(price);
            dataModel.setLongitude(longitude);
            dataModel.setLatitude(latitude);
            dataModel.setDate(date);

            stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        return data;
    }


}