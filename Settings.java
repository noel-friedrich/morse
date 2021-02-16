package com.example.morselife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class Settings extends AppCompatActivity {

    public static final String myPref = "switch";
    public static Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = this;

        Button btnSwitch = findViewById(R.id.btnSwitch);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnSave = findViewById(R.id.btnSave);

        if (getPreferenceValue().equals("on")) {
            btnSwitch.setText("ON");
            btnSwitch.setBackgroundColor(Color.parseColor("#2FC963"));
        } else {
            writeToPreference("off");
        }

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPreferenceValue().equals("off")) {
                    writeToPreference("on");
                    btnSwitch.setText("ON");
                    btnSwitch.setBackgroundColor(Color.parseColor("#2FC963"));
                } else {
                    writeToPreference("off");
                    btnSwitch.setText("OFF");
                    btnSwitch.setBackgroundColor(Color.parseColor("#C92F31"));
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getPreferenceValue()
    {
        SharedPreferences sp = getSharedPreferences(myPref,0);
        String str = sp.getString("switch","off");
        return str;
    }

    public void writeToPreference(String thePreference)
    {
        SharedPreferences.Editor editor = getSharedPreferences(myPref,0).edit();
        editor.putString("switch", thePreference);
        editor.commit();
    }

    public static Settings getSettings() {
        return settings;
    }
}