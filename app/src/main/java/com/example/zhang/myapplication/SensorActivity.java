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

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;
import static android.hardware.Sensor.TYPE_LIGHT;
import static android.hardware.Sensor.TYPE_PRESSURE;
import static android.hardware.Sensor.TYPE_PROXIMITY;
import static android.hardware.Sensor.TYPE_TEMPERATURE;
import static android.hardware.SensorManager.LIGHT_CLOUDY;
import static android.hardware.SensorManager.LIGHT_FULLMOON;
import static android.hardware.SensorManager.LIGHT_NO_MOON;
import static android.hardware.SensorManager.LIGHT_OVERCAST;
import static android.hardware.SensorManager.LIGHT_SHADE;
import static android.hardware.SensorManager.LIGHT_SUNLIGHT;
import static android.hardware.SensorManager.LIGHT_SUNLIGHT_MAX;
import static android.hardware.SensorManager.LIGHT_SUNRISE;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView lightTv;
    private TextView pressureTv;
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
        lightTv = (TextView) findViewById(R.id.Sensor_Light_Textview);
        pressureTv = (TextView) findViewById(R.id.Sensor_Pressure_Textview);
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
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType())
        {
            case Sensor.TYPE_PROXIMITY:
                if (sensorEvent.values[0] > 1e-5)
                    tv1.setText("无遮挡");
                else
                    tv1.setText("有遮挡");
                break;
            case Sensor.TYPE_ACCELEROMETER:
                String accelerometer = String.format("加速度:\nX:%.2f\nY:%.2f\nZ:%.2f",
                        sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                tv2.setText(accelerometer);
                break;
            case Sensor.TYPE_GRAVITY:
                String Gravity = String.format("重力:\nX:%.2f\nY:%.2f\nZ:%.2f",
                        sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                tv3.setText(Gravity);
                break;
            case Sensor.TYPE_PRESSURE:
                pressureTv.setText("压强："+sensorEvent.values[0]+"Pa");
                break;
            case Sensor.TYPE_LIGHT:
                String light = "";
                light = "光照强度："+sensorEvent.values[0]+"lx\n";
                if (sensorEvent.values[0] >= LIGHT_SUNLIGHT_MAX)
                    light += "沙漠地带";
                else if (sensorEvent.values[0] >= LIGHT_SUNLIGHT)
                    light += "万里无云阳光直射";
                else if (sensorEvent.values[0] >= LIGHT_SHADE)
                    light += "有阳光，有云彩";
                else if (sensorEvent.values[0] >= LIGHT_OVERCAST)
                    light += "多云";
                else if (sensorEvent.values[0] >= LIGHT_SUNRISE)
                    light += "日出";
                else if (sensorEvent.values[0] >= LIGHT_CLOUDY)
                    light += "阴雨，没有太阳 >_<";
                else if (sensorEvent.values[0] >= LIGHT_FULLMOON)
                    light += "夜晚，有月光or路灯";
                else if (sensorEvent.values[0] >= LIGHT_NO_MOON)
                    light += "漆黑一片";
                lightTv.setText(light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
