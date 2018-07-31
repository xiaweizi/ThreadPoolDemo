package com.example.xiaweizi.threaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ThreadPoolActivity.class));
                break;
            default:
                break;
        }
    }
}
