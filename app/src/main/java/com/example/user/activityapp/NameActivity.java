package com.example.user.activityapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = "Condition";
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    EditText etName;
    Button btnSubmit;
    Button btnSave;
    Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Log.d(TAG, "NameActivity: onRestart()");

        etName = findViewById(R.id.etName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);

        loadName();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "NameActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "NameActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "NameActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "NameActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "NameActivity: onDestroy()");
        saveName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
            Intent intent = new Intent();
            intent.putExtra("name", etName.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
                break;
            case R.id.btnSave:
                saveName();
                break;
            case R.id.btnLoad:
                loadName();
                break;
            default:break;

        }
    }

    private void loadName() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedName = sPref.getString(SAVED_TEXT, "");
        etName.setText(savedName);
        Toast.makeText(this, "Name loaded", Toast.LENGTH_SHORT).show();
    }

    private void saveName() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, etName.getText().toString());
        ed.commit();
        Toast.makeText(this, "Name saved", Toast.LENGTH_SHORT).show();
    }
}
