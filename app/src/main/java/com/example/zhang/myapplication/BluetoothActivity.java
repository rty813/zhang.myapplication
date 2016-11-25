package com.example.zhang.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        findViewById(R.id.btnOpenBluetooth1).setOnClickListener(this);
        findViewById(R.id.btnOpenBluetooth2).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenBluetooth1:
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,1);
                break;
            case R.id.btnOpenBluetooth2:
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                adapter.enable();
                break;
        }
    }
}
