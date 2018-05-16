package com.example.user.activityapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_date);

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String time = sdfTime.format(new Date(System.currentTimeMillis()));

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdfDate.format(new Date(System.currentTimeMillis()));

        TextView tvTime = findViewById(R.id.tvTime);
        tvTime.setText(time);

        TextView tvDate = findViewById(R.id.tvDate);
        tvDate.setText(date);


    }
}
