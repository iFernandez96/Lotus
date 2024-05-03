package com.example.lotus;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Tracker implements SensorEventListener {
    private final Context context;
    boolean isTracking = false;

    public Tracker(Context context) {
        this.context = context;
    }

    public void startTracking() {
        isTracking = true;
    }

    public void stopTracking() {
        isTracking = false;
    }

    //float past_roll = 0;
    //float past_pitch =0;
    //float past_yaw=0;
    boolean passed_threshold = false;

    private float[] quaternionToEuler(float[] quaternion) {
        // quaternions = {w, x, y, z}
        float w = quaternion[0];
        float x = quaternion[1];
        float y = quaternion[2];
        float z = quaternion[3];

        float t0 = 2.0f * (w * x + y * z);
        float t1 = 1.0f - 2.0f * (x * x + y * y);
        float roll = (float) Math.atan2(t0, t1);

        t0 = 2.0f * (w * y - z * x);
        t0 = Math.min(t0, +1.0f);
        t0 = Math.max(t0, -1.0f);
        float pitch = (float) Math.asin(t0);

        t0 = 2.0f * (w * z + x * y);
        t1 = 1.0f - 2.0f * (y * y + z * z);
        float yaw = (float) Math.atan2(t0, t1);

        // Convert radians to degrees
        roll = (float) Math.toDegrees(roll);
        pitch = (float) Math.toDegrees(pitch);
        yaw = (float) Math.toDegrees(yaw);

        return new float[]{roll, pitch, yaw}; // Euler angles in degrees
    }


    void initializeSensors() {
        createNotificationChannel();
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationVectorSensor == null) {
            Toast.makeText(context, "Rotation vector sensor not available.", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR && isTracking) {
            float diff;
            float[] quaternion = new float[4];
            quaternion[0] = event.values[0]; // Assigning w
            quaternion[1] = event.values[1]; // Assigning x
            quaternion[2] = event.values[2]; // Assigning y
            quaternion[3] = event.values[3]; // Assigning z

            float[] euler = quaternionToEuler(quaternion);
            float roll = euler[0];
            float pitch = euler[1];
            float yaw = euler[2];

            /*if (past_yaw ==0){
                past_yaw = yaw;
            }
            if (past_pitch ==0){
                past_pitch = pitch;
            }
            if (past_roll ==0) {
                past_roll = roll;
            }
            */
            //diff = Math.abs(past_roll-roll);
            //if (diff>90){
            //    past_roll = roll;
            //    Toast.makeText(context,"Passed the 90 degree roll threshold" + roll,Toast.LENGTH_SHORT).show();
            //}
            //diff = Math.abs(past_yaw-yaw);

            if (yaw < 85 && !passed_threshold) {
                passed_threshold = true;
                showHeadDipNotification();
                Toast.makeText(context, "Passed the 85 degree yaw threshold" + yaw, Toast.LENGTH_SHORT).show();
            }

            if (yaw > 85 && passed_threshold) {
                passed_threshold = false;
            }
            //diff = Math.abs(past_pitch-pitch);
            //if(diff>90){
            //    past_pitch = pitch;
            //    Toast.makeText(context,"Passed the 90 degree pitch threshold" + pitch,Toast.LENGTH_SHORT).show();
            //}
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    // Sensor has high accuracy
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    Toast.makeText(context, "Medium Quaternion accuracy...", Toast.LENGTH_SHORT).show();
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    Toast.makeText(context, "Low Quaternion accuracy...", Toast.LENGTH_SHORT).show();
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    Toast.makeText(context, "Quaternions unreliable...", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + accuracy);
            }
        }
    }

    private void createNotificationChannel() {
        CharSequence name = "Head Dip Channel";
        String description = "Channel for head dip notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH; // Changed to IMPORTANCE_HIGH
        NotificationChannel channel = new NotificationChannel("head_dip_channel", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showHeadDipNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "head_dip_channel")
                .setSmallIcon(R.drawable.hunchicon1)
                .setContentTitle("Head Dip Detected")
                .setContentText("You dipped your head below the threshold.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
