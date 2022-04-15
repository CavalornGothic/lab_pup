package com.example.lab_pup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private float defaultX = 0;
    private float defaultY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private SensorEventListener listener = new SensorEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

            TextView xSensor = (TextView)findViewById(R.id.x_acc_value);
            xSensor.setText(String.valueOf(xValue));
            TextView ySensor = (TextView)findViewById(R.id.y_acc_value);
            ySensor.setText(String.valueOf(yValue));
            TextView zSensor = (TextView)findViewById(R.id.z_acc_value);
            zSensor.setText(String.valueOf(zValue));

            LinearLayout mainLinearl = findViewById(R.id.mainLinearl);
            mainLinearl.setBackgroundColor(Color.rgb(Math.abs(xValue), Math.abs(yValue), Math.abs(zValue)));

            Button buttonSquare = findViewById(R.id.square);
            float currentX = Math.round(xValue);
            float currentY = Math.round(yValue);
            float currentZ = Math.round(zValue);
            if(currentX != defaultX || currentY != defaultY)
            {
                buttonSquare.setX(defaultX + (currentX * currentZ));
                buttonSquare.setY(defaultY + (currentY * currentZ));
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void start_click(View view) {
        Button buttonSquare = findViewById(R.id.square);
        defaultX = buttonSquare.getX();
        defaultY = buttonSquare.getY();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop_click(View view) {
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}