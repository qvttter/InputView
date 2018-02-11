package com.bzt.inputview.inputview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bzt.inputview.bottominputview.InputView;

public class MainActivity extends AppCompatActivity {

    private InputView inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputView = findViewById(R.id.input);
        inputView.setCallBack(new InputView.OnInputCallBack() {
            @Override
            public void onInput(String content) {
                Toast.makeText(MainActivity.this,content,Toast.LENGTH_LONG).show();
            }
        });
    }
}
