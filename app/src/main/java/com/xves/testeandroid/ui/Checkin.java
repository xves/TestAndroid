package com.xves.testeandroid.ui;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xves.testeandroid.R;
import com.xves.testeandroid.data.network.Request;
import com.xves.testeandroid.viewmodel.MainViewModel;


public class Checkin extends DialogFragment implements
        android.view.View.OnClickListener {

    Button selectBtn;
    EditText edtName, edtEmail;
    String eventid, name, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventid = getArguments().getString("eventid");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.checkin, container, false);

        selectBtn = v.findViewById(R.id.Select);
        edtName = v.findViewById(R.id.edtName);
        edtEmail = v.findViewById(R.id.edtEmail);

        selectBtn.setOnClickListener(v12 -> {
            name = edtName.getText().toString();
            email = edtEmail.getText().toString();

            if (MainViewModel.Connection(getActivity())) {
                if (!name.equals("") && !email.equals("")) {
                    Request.Checkin(getActivity(), eventid, name, email);
                    Toast.makeText(getActivity(), getActivity().getString(R.string.chekin), Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.empty), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.connection_off), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        getDialog().setCanceledOnTouchOutside(true);
        return v;

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dismiss();
    }

    @Override
    public void onClick(View v) {

    }

}