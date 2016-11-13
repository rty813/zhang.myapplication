package com.example.zhang.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static android.hardware.Sensor.TYPE_PROXIMITY;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tv1,tv2,tv3;
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv1 = (TextView) findViewById(R.id.Sensor_Proximity_textview);
        tv2 = (TextView) findViewById(R.id.Sensor_Accelerometer_textview);
        tv3 = (TextView) findViewById(R.id.Sensor_Gravity_TextView);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_PROXIMITY:
                String distance = "距离："+sensorEvent.values[0];
                tv1.setText(distance);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                String accelerometer = "加速度：\n"+"X:"+sensorEvent.values[0]
                        +"\nY:"+sensorEvent.values[1]
                        +"\nZ:"+sensorEvent.values[2];
                tv2.setText(accelerometer);
                break;
            case Sensor.TYPE_GRAVITY:
                String Gravity = "重力：\n"+"X:"+sensorEvent.values[0]
                        +"\nY:"+sensorEvent.values[1]
                        +"\nZ:"+sensorEvent.values[2];
                tv3.setText(Gravity);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
