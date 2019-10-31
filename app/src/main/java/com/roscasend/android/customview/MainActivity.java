package com.roscasend.android.customview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyRadioButton customView = findViewById(R.id.radioButton2);
//        customView.setImageResource(R.drawable.american);
        customView.setImageResource(R.drawable.american);

//
//        findViewById(R.id.maximize).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.maximize) {
            customView.setMaximumSize();
        }
    }
}
