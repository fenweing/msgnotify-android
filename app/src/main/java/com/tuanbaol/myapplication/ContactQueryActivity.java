package com.tuanbaol.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.tuanbaol.myapplication.util.HttpUtil;

public class ContactQueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_query);
        TextView textView = findViewById(R.id.textView);
    }

    public static void main(String[] args) throws Exception {
        String s = HttpUtil.doGet("http://www.tuanbaol.com");
        System.out.println(s);
    }
}
