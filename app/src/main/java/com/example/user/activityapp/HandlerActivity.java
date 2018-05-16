package com.example.user.activityapp;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HandlerActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs" ;

    ProgressBar progressBar, pbDownload;
    Handler handlerDownload, handlerConnect;
    TextView tvInfo;
    Button btnDownload, btnConnect;

    final int STATUS_NONE = 0; // нет подключения
    final int STATUS_CONNECTING = 1; // подключаемся
    final int STATUS_CONNECTED = 2; // подключено
    final int STATUS_DOWNLOAD_START = 3; // загрузка началась
    final int STATUS_DOWNLOAD_FILE = 4; // файл загружен
    final int STATUS_DOWNLOAD_END = 5; // загрузка закончена
    final int STATUS_DOWNLOAD_NONE = 6; // нет файлов для загрузки

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvInfo = findViewById(R.id.tvInfo);
        btnDownload = findViewById(R.id.btnDownload);
        btnConnect = findViewById(R.id.btnConnect);
        progressBar = findViewById(R.id.progressBar);
        pbDownload = findViewById(R.id.pbDownload);

        handlerDownload = new Handler() {
            public void handleMessage(android.os.Message msg) {
                progressBar.setVisibility(View.VISIBLE);
                tvInfo.setText("Закачано файлов: " + msg.what);
                if (msg.what == 10) {
                    btnDownload.setEnabled(true);
                    btnConnect.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        handlerConnect = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnect.setEnabled(true);
                        tvInfo.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        tvInfo.setText("Connecting");
                        break;
                    case STATUS_CONNECTED:
                        progressBar.setVisibility(View.GONE);
                        tvInfo.setText("Connected");
                        break;
                    case STATUS_DOWNLOAD_START:
                        tvInfo.setText("Start download " + msg.arg1 + " files");
                        pbDownload.setMax(msg.arg1);
                        pbDownload.setProgress(0);
                        pbDownload.setVisibility(View.VISIBLE);
                        break;
                    case STATUS_DOWNLOAD_FILE:
                        tvInfo.setText("Downloading. Left " + msg.arg2 + " files");
                        pbDownload.setProgress(msg.arg1);
                        saveFile((byte[]) msg.obj);
                        break;
                    case STATUS_DOWNLOAD_END:
                        tvInfo.setText("Download complete!");
                        break;
                    case STATUS_DOWNLOAD_NONE:
                        tvInfo.setText("No files for download");
                        break;
                }
            }
        };
        handlerConnect.sendEmptyMessage(STATUS_NONE);
    }

    private void saveFile(byte[] obj) {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDownload:
                btnDownload.setEnabled(false);
                btnConnect.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            // долгий процесс
                            downloadFiles();
                            handlerDownload.sendEmptyMessage(i);
                            // пишем лог
                            Log.d(LOG_TAG, "i = " + i);
                        }
                    }
                });
                t.start();
                break;
            case R.id.btnConnect:
                Thread thread = new Thread(new Runnable() {
                    Message msg;
                    byte[] file;
                    Random rand = new Random();

                    public void run() {
                        try {
                            // устанавливаем подключение
                            handlerConnect.sendEmptyMessage(STATUS_CONNECTING);
                            TimeUnit.SECONDS.sleep(3);

                            // подключение установлено
                            handlerConnect.sendEmptyMessage(STATUS_CONNECTED);

                            // определяем кол-во файлов
                            TimeUnit.SECONDS.sleep(2);
                            int filesCount = rand.nextInt(8);

                            if (filesCount == 0) {
                                // сообщаем, что файлов для загрузки нет
                                handlerConnect.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
                                // и отключаемся
                                TimeUnit.MILLISECONDS.sleep(1500);
                                handlerConnect.sendEmptyMessage(STATUS_NONE);
                                return;
                            }

                            // загрузка начинается
                            // создаем сообщение, с информацией о количестве файлов
                            msg = handlerConnect.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                            // отправляем
                            handlerConnect.sendMessage(msg);

                            for (int i = 1; i <= filesCount; i++) {
                                // загружается файл
                                file = downloadFile();
                                // создаем сообщение с информацией о порядковом номере
                                // файла,
                                // кол-вом оставшихся и самим файлом
                                msg = handlerConnect.obtainMessage(STATUS_DOWNLOAD_FILE, i,
                                        filesCount - i, file);
                                // отправляем
                                handlerConnect.sendMessage(msg);
                            }

                            // загрузка завершена
                            handlerConnect.sendEmptyMessage(STATUS_DOWNLOAD_END);

                            // отключаемся
                            TimeUnit.MILLISECONDS.sleep(1500);
                            handlerConnect.sendEmptyMessage(STATUS_NONE);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            default:
                break;
        }
    }


    void downloadFiles() {
        // пауза - 1 секунда
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    byte[] downloadFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

}
