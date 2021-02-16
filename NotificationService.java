package com.example.morselife;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.HashMap;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    Context context;

    public static NotificationService notificationService;
    private static int delay_between_letters = 1000;
    private static int delay_between_codes = 400;
    private static int delay_on_pause = 2000;
    private static int delay_on_dit = 200;
    private static int delay_on_dah = 400;

    private static String finalText;

    private static Boolean playing_msg = false;

    public Thread t;

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        notificationService = this;

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void play_msg(String msg) {
        HashMap<Character, String> codes = load_codes();
        for (char letter: msg.replace(" ","_").toCharArray()) {
            System.out.println(letter);
            String whole_code = codes.get(Character.toUpperCase(letter));
            for (char code: whole_code.toCharArray()) {
                if (code == '.' && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrate(delay_on_dit);
                    try {
                        Thread.sleep(delay_on_dit);
                    } catch (InterruptedException e) {
                        playing_msg = false;
                        System.out.println("THREAD ENDED!");
                        return;
                    }
                } else if (code == '-' && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrate(delay_on_dah);
                    try {
                        Thread.sleep(delay_on_dah);
                    } catch (InterruptedException e) {
                        playing_msg = false;
                        System.out.println("THREAD ENDED!");
                        return;
                    }
                } else if (code == '#' && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        Thread.sleep(delay_on_pause);
                    } catch (InterruptedException e) {
                        playing_msg = false;
                        System.out.println("THREAD ENDED!");
                        return;
                    }
                }
                try {
                    Thread.sleep(delay_between_codes);
                } catch (InterruptedException e) {
                    playing_msg = false;
                    System.out.println("THREAD ENDED!");
                    return;
                }
            }
            try {
                Thread.sleep(delay_between_letters);
            } catch (InterruptedException e) {
                playing_msg = false;
                System.out.println("THREAD ENDED!");
                return;
            }
        }
        playing_msg = false;
        System.out.println("THREAD ENDED!");
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
        codes.put('_',"#");
        return codes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void vibrate(int milliseconds) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(milliseconds);
        }
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onNotificationPosted(StatusBarNotification sbn) {

        Bundle extras = sbn.getNotification().extras;
        String text = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();

        start_thread(text);
    }

    public void start_thread(String msg) {
        if ( Settings.getSettings().getPreferenceValue() != null) {
            if (!playing_msg && Settings.getSettings().getPreferenceValue().equals("on")) {
                if (msg.contains("messages") || msg.contains("%")  || msg.contains("Web") || msg.contains("USB"))
                    return;
                playing_msg = true;
                System.out.println(msg);
                finalText = msg;
                t = new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        System.out.println("THREAD STARTED!");
                        try {
                            vibrate(3000);
                            Thread.sleep(4000);
                            play_msg(finalText);
                        } catch (Exception e) {
                            playing_msg = false;
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        }
    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");
    }

    public static NotificationService getNotificationService() {
        return notificationService;
    }

    public Thread getT() {
        return t;
    }

    public static void setFinalText(String finalText) {
        NotificationService.finalText = finalText;
    }
}