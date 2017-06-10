package com.lambda.auto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.lambda.auto.ui.AutoNextLineLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoNextLineLinearLayout autoLine;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();
        initView();


    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            data.add("lambda" + i);
        }
    }

    private void initView() {
        autoLine = (AutoNextLineLinearLayout) findViewById(R.id.auto_line);

        for (int i = 0; i < data.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(data.get(i));
            tv.setBackgroundResource(R.drawable.normal_gray_corners13);
            tv.setTextColor(0xff333333);
            tv.setTag(i);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(13);
            tv.setPadding(20, 12, 20, 12);
            autoLine.addView(tv);
        }
    }
}
