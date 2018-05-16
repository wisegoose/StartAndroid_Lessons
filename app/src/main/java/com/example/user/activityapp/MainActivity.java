package com.example.user.activityapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "Condition";
    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;
    final int REQUEST_CODE_NAME = 3;

    TextView tvText;
    Button btnSecondActivity;
    Button btnDialogActivity;
    Button btnShowDateAndTime;
    Button btnNameActivity;
    Button btnColor;
    Button btnAlign;
    Button btnWeb;
    Button btnCall;
    Button btnMap;
    Button btnHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate()");

        tvText = findViewById(R.id.tvText);

    //first button
        btnSecondActivity = findViewById(R.id.btnSecondActivity);
        btnSecondActivity.setOnClickListener(this);
    //second button
        btnDialogActivity = findViewById(R.id.btnDialogActivity);
        btnDialogActivity.setOnClickListener(this);
    //third button
        btnShowDateAndTime = findViewById(R.id.btnShowDateAndTime);
        btnShowDateAndTime.setOnClickListener(this);
    //fourth button
        btnNameActivity = findViewById(R.id.btnNameActivity);
        btnNameActivity.setOnClickListener(this);
    //fifth button
        btnColor = findViewById(R.id.btnColor);
        btnColor.setOnClickListener(this);
    //sixth button
        btnAlign = findViewById(R.id.btnAlign);
        btnAlign.setOnClickListener(this);
    //seventh button
        btnWeb = findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(this);
    //eighth button
        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
    //ninth button
        btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
    //tenth button clickable
    //eleventh button
        btnHandler = findViewById(R.id.btnHandler);
        btnHandler.setOnClickListener(this);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnSecondActivity:
                intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDialogActivity:
                intent = new Intent(this, DialogActivity.class);
                startActivity(intent);
                break;
            case R.id.btnShowDateAndTime:
                intent = new Intent("com.example.intent.action.showDataAndTime");
                startActivity(intent);
                break;
            case R.id.btnNameActivity:
                intent = new Intent(this, NameActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NAME);
                break;
            case R.id.btnColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.btnAlign:
                intent = new Intent(this, AlignActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGN);
                break;
            case R.id.btnWeb:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ya.ru")));
                break;
            case R.id.btnMap:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:55.754283,37.62002"));
                startActivity(intent);
                break;
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:12345"));
                startActivity(intent);
                break;
            case R.id.btnHandler:
                intent = new Intent(this, HandlerActivity.class);
                startActivity(intent);
                break;

            default: break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NAME:
                    String name = data.getStringExtra("name");
                    tvText.setText("Welcome " + name);
                    break;
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.BLACK);
                    tvText.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGN:
                    int align = data.getIntExtra("alignment", Gravity.CENTER);
                    tvText.setGravity(align);
                    break;
            }

        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}
