package com.example.katsuro.mycalcv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    private String message;
    private TextView textViewHistory;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textViewHistory = (TextView) findViewById(R.id.textViewHistory);
        textViewHistory.setText(message);
    }

    public void buttonClickBack(View view)
    {
        finish();
    }
}
