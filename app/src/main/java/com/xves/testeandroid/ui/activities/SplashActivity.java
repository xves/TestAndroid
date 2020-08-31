package com.xves.testeandroid.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xves.testeandroid.data.network.DataBase;
import com.xves.testeandroid.R;
import com.xves.testeandroid.viewmodel.MainViewModel;
import com.xves.testeandroid.data.network.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs;
    String res, status, data_time;
    private DataBase base;

    ImageView iconLogo, iconSlogan;
    Animation fromTop, fromLeft, fromRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = getSharedPreferences("PREFUSUARIO", 0);
        base = new DataBase(this);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data_time = df.format(Calendar.getInstance().getTime());

        iconLogo = findViewById(R.id.instrutor);
        iconSlogan = findViewById(R.id.virtual);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.anim_from_top_to_bottom);
        fromLeft = AnimationUtils.loadAnimation(this, R.anim.anim_from_left_to_right);
        fromRight = AnimationUtils.loadAnimation(this, R.anim.anim_from_right_to_left);

        iconLogo.setAnimation(fromLeft);
        iconSlogan.setAnimation(fromRight);

        if (MainViewModel.Connection(this)) {
            Request.getEvents(this);
        } else {
            Toast.makeText(this, getString(R.string.connection_off), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
