package com.example.user.activityapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColorActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRed;
    Button btnBlue;
    Button btnGreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        btnRed = findViewById(R.id.btnRed);
        btnRed.setOnClickListener(this);
        btnBlue = findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(this);
        btnGreen = findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btnRed:
                intent.putExtra("color", Color.RED);
                break;
            case R.id.btnBlue:
                intent.putExtra("color", Color.BLUE);
                break;
            case R.id.btnGreen:
                intent.putExtra("color", Color.GREEN);
                break;
            default:break;
        }
        setResult(RESULT_OK, intent);
        finish();

    }
}
