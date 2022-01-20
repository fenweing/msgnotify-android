package com.tuanbaol.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.tuanbaol.myappalication.MESSAGE";
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    // for notification trigger
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView viewById = findViewById(R.id.textView3);
//        Intent intent = new Intent(MainActivity.this, MyNotifiService.class);//启动服务
//        startService(intent);//启动服务
        // 判断是否开启监听通知权限
        if (NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName())) {
            Intent serviceIntent = new Intent(this, MyNotifiService.class);
            startService(serviceIntent);
        } else {
//            // 去开启监听通知权限
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
//            @Override
//            public void onClick(View v) {
        //打开监听引用消息Notification access
//            Intent intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
//            startActivity(intent_s);
//            }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Intent intent_p = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
//            startActivity(intent_p);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    String getMsg = sp.getString("getMsg", "");
//                    if (!TextUtils.isEmpty(getMsg)) {
//                        viewById.setText(getMsg);
//                    }
//                    try {
//                        Thread.currentThread().sleep(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
////
//            }
//        });
    }

    /**
     * Called when the user taps the Send button
     */
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
//
//    public void queryContact(View view) {
//        Intent intent = new Intent(this, ContactQueryActivity.class);
//        startActivity(intent);
//    }
}
