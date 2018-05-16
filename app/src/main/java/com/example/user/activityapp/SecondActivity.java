package com.example.user.activityapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.activityapp.sqlite.DBHelper;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "Condition";
    DBHelper dbHelper;

    EditText etName, etPhoneNumber, etID;
    Button btnSubmit, btnAdd, btnRead, btnClear, btnDelete, btnSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "SecondActivity: onCreate()");

        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etID = findViewById(R.id.etID);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "SecondActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "SecondActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SecondActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SecondActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SecondActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SecondActivity: onDestroy()");
    }


    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String id = etID.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.btnSubmit:
            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
            break;
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_PHONE_NUMBER, phoneNumber);
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                etName.setText("");
                etPhoneNumber.setText("");
                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int phoneNumberIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE_NUMBER);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", phoneNumber = " + cursor.getString(phoneNumberIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
            case R.id.btnSet:
                if (id.equalsIgnoreCase("")){
                    break;
                }
                contentValues.put(DBHelper.KEY_PHONE_NUMBER, phoneNumber);
                contentValues.put(DBHelper.KEY_NAME, name);
                int count = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {id});

                Log.d("mLog", "updates rows count = " + count);

            case R.id.btnDelete:
                if (id.equalsIgnoreCase("")){
                    break;
                }
                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);

                Log.d("mLog", "deleted rows count = " + delCount);
        }
        dbHelper.close();
    }
}
