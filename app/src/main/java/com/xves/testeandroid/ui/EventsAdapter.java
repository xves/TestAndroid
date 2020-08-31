package com.xves.testeandroid.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.xves.testeandroid.R;
import com.xves.testeandroid.data.Events;
import com.xves.testeandroid.ui.activities.EventsDetailsActivity;
import com.xves.testeandroid.ui.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.HolderClientes> {
    List<Events> list;
    MainActivity activity;


    public EventsAdapter(List<Events> list, Activity activity) {
        this.list = list;
        this.activity = (MainActivity) activity;
    }

    class HolderClientes extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDate;
        CardView cardView;
        ImageView image, img_share, img_check_in;

        public HolderClientes(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            cardView = itemView.findViewById(R.id.cardView);
            image = itemView.findViewById(R.id.image);
            img_check_in = itemView.findViewById(R.id.img_check_in);
            img_share = itemView.findViewById(R.id.img_share);
        }
    }

    @Override
    public EventsAdapter.HolderClientes onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventsAdapter.HolderClientes(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.HolderClientes holder, int position) {
        Events dataModel = list.get(position);
        holder.txtTitle.setText(dataModel.getTitle());
        String data = dataModel.getDate();
        long date = Long.valueOf(data);
        Date dt = new Date(date);
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        String dat = spf.format(dt);
        holder.txtDate.setText(dat);

        Glide.with(activity).load(dataModel.getImage()).into(holder.image);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, EventsDetailsActivity.class);
            intent.putExtra("name", dataModel.getTitle());
            intent.putExtra("image_event", dataModel.getImage());
            intent.putExtra("description", dataModel.getDescrition());
            activity.startActivity(intent);
        });

        holder.img_check_in.setOnClickListener(v -> {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            String eventid = String.valueOf(dataModel.getId());
            DialogFragment dialogFragment = new Checkin();
            Bundle args = new Bundle();
            args.putString("eventid", eventid);
            dialogFragment.setArguments(args);
            dialogFragment.show(ft, "dialog");
        });

        holder.img_share.setOnClickListener(v -> {
            Toast.makeText(activity, activity.getString(R.string.share), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

