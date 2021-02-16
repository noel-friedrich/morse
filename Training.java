package com.example.morselife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Training extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    private List<String> out = new ArrayList<>();

    private int correct;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Button btnBack = findViewById(R.id.btnBack);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        textView = findViewById(R.id.textView);

        get_question();
        set_question();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Training.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_answer(4);
            }
        });
    }

    private void check_answer(int answer) {
        Boolean win = correct == answer;
        if (win) {
            Toast toast =  Toast.makeText(getApplicationContext(),"RICHTIG!",Toast.LENGTH_SHORT);
            toast.show();
            get_question();
            set_question();
        } else {
            Toast toast =  Toast.makeText(getApplicationContext(),"FALSCH!",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void set_question() {
        textView.setText(out.get(0));
        btn1.setText(out.get(1));
        btn2.setText(out.get(2));
        btn3.setText(out.get(3));
        btn4.setText(out.get(4));
    }

    private void get_question() {
        HashMap<Character, String> codes = load_codes();
        out.clear();
        Object correctKey = codes.keySet().toArray()[new Random().nextInt(codes.keySet().toArray().length)];
        for (int i = 0; i < 5; i++) {
            Object randomKey = codes.keySet().toArray()[new Random().nextInt(codes.keySet().toArray().length)];
            if(randomKey == correctKey) {
                i--;
            } else {
                out.add(codes.get(randomKey));
            }
        }
        out.set(0, String.valueOf(correctKey));
        correct = (int) ((Math.random() * 4) + 1);
        out.set(correct, codes.get(correctKey));
    }

    public HashMap<Character, String> load_codes() {
        HashMap<Character, String> codes = new HashMap<Character, String>();
        codes.put('T',"-");
        codes.put('E',".");
        codes.put('M',"--");
        codes.put('N',"-.");
        codes.put('A',".-");
        codes.put('I',"..");
        codes.put('O',"---");
        codes.put('G',"--.");
        codes.put('K',"-.-");
        codes.put('D',"-..");
        codes.put('W',".--");
        codes.put('R',".-.");
        codes.put('U',"..-");
        codes.put('S',"...");
        codes.put('Ö',"---.");
        codes.put('Q',"--.-");
        codes.put('Z',"--..");
        codes.put('Y',"-.--");
        codes.put('C',"-.-.");
        codes.put('X',"-..-");
        codes.put('B',"-...");
        codes.put('J',".---");
        codes.put('P',".--.");
        codes.put('Ä',".-.-");
        codes.put('L',".-..");
        codes.put('Ü',"..--");
        codes.put('F',"..-.");
        codes.put('V',"...-");
        codes.put('H',"....");
        return codes;
    }
}