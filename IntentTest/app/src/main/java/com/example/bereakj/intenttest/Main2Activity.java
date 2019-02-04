package com.example.bereakj.intenttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import vo.Member;

public class Main2Activity extends AppCompatActivity {

    private EditText name;
    private EditText age;

    private Intent intent;

    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initialize();
        getExt();
    }

    public void initialize() {
        name = (EditText)findViewById(R.id.editText);
        age = (EditText)findViewById(R.id.editText2);

        intent = getIntent();
    }

    public void getExt() {
        String n = intent.getStringExtra("name");
        int a = intent.getIntExtra("age", 0);
        member = (Member)intent.getSerializableExtra("member");

        if(member == null) setText(n, a);
        else setText(member.getName(), member.getAge());
    }

    public void setText(String n, int a) {
        name.setText(n);
        age.setText(a + "");
    }

    public void onClose(View v) {
        member = new Member(name.getText().toString(), Integer.parseInt(age.getText().toString()));

        intent.putExtra("member", member);

        setResult(RESULT_OK, intent);
        finish();
    }
}
