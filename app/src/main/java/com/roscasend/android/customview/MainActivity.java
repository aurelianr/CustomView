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


        MyRadioButton radioButton1 = findViewById(R.id.radioButton1);
        radioButton1.setImageResource(R.drawable.american);

        MyRadioButton radioButton2 = findViewById(R.id.radioButton2);
        radioButton2.setImageResource(R.drawable.mastercard);

        MyRadioButton radioButton3 = findViewById(R.id.radioButton);
        radioButton3.setImageResource(R.drawable.prepaid);

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
