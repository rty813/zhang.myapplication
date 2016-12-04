package com.example.zhang.myapplication;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int num;
    private TextView Tv;
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(MainActivity.this,"ACTION_DOWN",Toast.LENGTH_SHORT).show();
                        System.out.println("ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(MainActivity.this,"ACTION_UP",Toast.LENGTH_SHORT).show();
                        System.out.println("ACTION_UP");
                        break;
                }
                return true;
            }
        });

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);


        Tv = (TextView) findViewById(R.id.textview);
        findViewById(R.id.btnchange).setOnClickListener(this);
        findViewById(R.id.btnSend).setOnClickListener(this);
        findViewById(R.id.btnStartSliderActivity).setOnClickListener(this);
        findViewById(R.id.btnStartTabsActivity).setOnClickListener(this);
        findViewById(R.id.btnStartSensor).setOnClickListener(this);
        findViewById(R.id.btnStartBluetooth).setOnClickListener(this);
        findViewById(R.id.btnStartAsyncTaskActivity).setOnClickListener(this);
        findViewById(R.id.btnStartHttpActivity).setOnClickListener(this);
        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.btnStartSocketActivity).setOnClickListener(this);
        findViewById(R.id.btnStartBaiduSpeechActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                System.exit(0);
                break;
            case R.id.btnchange:
                Tv.setText(num+"");
                num++;
                break;
            case R.id.btnSend:
                sendBroadcast(new Intent(this,MyReceiver.class));
                break;
            case R.id.btnStartBluetooth:
                startActivity(new Intent(this,BluetoothActivity.class));
                break;
            case R.id.btnStartSliderActivity:
                startActivity(new Intent(this,SliderActivity.class));
                break;
            case R.id.btnStartTabsActivity:
                startActivity(new Intent(this,Tabs.class));
                break;
            case R.id.btnStartSensor:
                startActivity(new Intent(this,SensorActivity.class));
                break;
            case R.id.btnStartAsyncTaskActivity:
                startActivity(new Intent(this,AsyncTaskActivity.class));
                break;
            case R.id.btnStartHttpActivity:
                startActivity(new Intent(this,HttpActivity.class));
                break;
            case R.id.btnStartSocketActivity:
                startActivity(new Intent(this,SocketActivity.class));
                break;
            case R.id.btnStartBaiduSpeechActivity:
                startActivity(new Intent(this,BaiduSpeechActivity.class));
                break;
        }
    }



}
