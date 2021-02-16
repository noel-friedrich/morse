package com.example.morselife;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etmsg;
    TextView out;
    public static MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(R.layout.activity_main);
        etmsg = findViewById(R.id.etmsg);

        Button btnSend = findViewById(R.id.btnSend);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnTraining = findViewById(R.id.btnTraining);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etmsg.getText().toString().trim();
                etmsg.getText().clear();
                if (NotificationService.getNotificationService() != null)
                    NotificationService.getNotificationService().start_thread(msg);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotificationService.getNotificationService().getT() != null)
                    NotificationService.getNotificationService().getT().interrupt();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Settings.class);
                startActivity(intent);
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        Training.class);
                startActivity(intent);
            }
        });
    }


}