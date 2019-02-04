package com.example4.bereakj.filetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {

    private EditText fileName;
    private EditText content;

    private FileInputStream fis;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initialize();
    }

    public void initialize() {
        fileName = (EditText)findViewById(R.id.editText);
        content = (EditText)findViewById(R.id.editText2);
    }

    public void onRead(View v) {
        try {
            File f = new File(fileName.getText().toString());
            if(f.exists()) {
                fis = openFileInput(fileName.getText().toString());
                StringBuffer sb = new StringBuffer();
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
//            String str = buffer.readLine();
//            while(str != null) {
//                sb.append(str + "\n");
//                str = buffer.readLine();
//            }
//            content.setText(sb);
//            buffer.close();
//            fis.close();
                byte[] arr = new byte[40];
                int len = 0;
                while (true) {
                    len = fis.read(arr);
                    if (len <= 0) {
                        break;
                    }
                    String s = new String(arr);
                    sb.append(s);
                    if (fis.available() < 40) {
                        Arrays.fill(arr, 0, 40, (byte) 0);
                    }
                }
                content.setText(sb.toString());
                fis.close();
            }
            else {
                toast("파일을 찾을 수 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            toast("파일을 찾을 수 없습니다. \n 작성부터 해주세요.");
        }
    }

    public void onWrite(View v) {
        try {
            fos = openFileOutput(fileName.getText().toString(), MODE_APPEND);
            fos.write(content.getText().toString().getBytes());
            fos.close();
            clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        fileName.setText("");
        content.setText("");
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
