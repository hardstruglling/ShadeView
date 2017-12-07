package com.jk.shadeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jk.shadeview.view.LoadTextView;

public class MainActivity extends AppCompatActivity {

    private LoadTextView loadTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadTextView = ((LoadTextView) findViewById(R.id.loadText));
        loadTextView.setDuration(1000);
        loadTextView.startShow();
    }
}
