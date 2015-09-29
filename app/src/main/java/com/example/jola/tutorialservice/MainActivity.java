package com.example.jola.tutorialservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start service
        intent = new Intent(getApplicationContext(), MusicPlayer.class);
    }

    public void startPlayer(View view) {
        startService(intent);
    }

    public void stopPlayer(View view) {
        stopService(intent);
    }
}
