package com.xves.testeandroid.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xves.testeandroid.R;


public class EventsDetailsActivity extends AppCompatActivity {

    TextView name, txtDescription, txtTitle;
    ImageView voltar, image;
    String image_event, description, name_event;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);


        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        voltar = findViewById(R.id.imgBack);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);

        voltar.setOnClickListener(this::btnVoltar);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            name_event = b.getString("name");
            image_event = b.getString("image_event");
            description = b.getString("description");
        }

        Glide.with(this).load(image_event).into(image);
        txtTitle.setText(name_event);
        txtDescription.setText(description);

    }

    private void btnVoltar(View view) {
        finish();
    }
}