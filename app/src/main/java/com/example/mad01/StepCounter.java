 package com.example.mad01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;

import kotlin.jvm.internal.Intrinsics;

 public class StepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView StepCounter;
    private SensorManager sensorManager;
    private Sensor sensor1;
    private boolean isStepCounterPresent;

    private int stepCount=0;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

       //for screen awake
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StepCounter = findViewById(R.id.ST_STEPS);
        sensorManager =(SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){

            sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepCounterPresent = true;
        }else{
            StepCounter.setText("0");
            isStepCounterPresent = false;
        }
    }

     @Override
     public void onSensorChanged(SensorEvent event) {

        if(event.sensor == sensor1) {
            stepCount = (int) event.values[0];
            StepCounter.setText(String.valueOf(stepCount));

        }
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {

     }


     @Override
     protected void onResume() {
         super.onResume();

         if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
             sensorManager.registerListener(this,sensor1,SensorManager.SENSOR_DELAY_NORMAL );
     }

     @Override
     protected void onPause() {
         super.onPause();
         if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
             sensorManager.unregisterListener(this,sensor1);

     }



     @NonNull
     @Contract(pure = true)
     public final String getCalories(int stepCount) {
         int Cal = (int)((double)stepCount * 0.045D);
         return Cal + " calories";
     }

     @NonNull
     public final String getDistanceCovered(int stepCount) {
         int feet = (int)((double)stepCount * 2.5D);
         double distance = (double)feet / 3.281D;
         String dis2 = "%.2f";
         Object[] o = new Object[]{distance};
         String var10000 = String.format(dis2, Arrays.copyOf(o, o.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(format, *args)");
         String dis = var10000;
         double finalDistance = Double.parseDouble(dis);
         return finalDistance + " meter";
     }
 }