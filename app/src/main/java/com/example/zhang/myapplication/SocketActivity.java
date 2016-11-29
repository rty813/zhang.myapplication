package com.example.zhang.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        intent = new Intent(SocketActivity.this,SocketService.class);
        findViewById(R.id.btnOpenSocketService).setOnClickListener(this);
        findViewById(R.id.btnCloseSocketService).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenSocketService:
                startService(intent);
                break;
            case R.id.btnCloseSocketService:
                stopService(intent);
                break;
        }
    }
}
