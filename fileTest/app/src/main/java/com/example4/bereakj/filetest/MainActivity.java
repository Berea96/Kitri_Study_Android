package com.example4.bereakj.filetest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    public void initialize() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tv = (TextView)findViewById(R.id.textView);
    }

    public void onRead(View v) {
        String str = "";
        str += "str : " + sharedPreferences.getString("str", "") + "\n";
        str += "bool : " + sharedPreferences.getBoolean("bool", false) + "\n";
        str += "int : " + sharedPreferences.getInt("int", 0);
        tv.setText(str);
    }

    public void onWrite(View v) {
        editor.putString("str", "aaa");
        editor.putBoolean("bool", true);
        editor.putInt("int", 123);
        editor.commit();
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
