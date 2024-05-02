package com.example.lotus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

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

        return new float[] {roll, pitch, yaw}; // Euler angles in degrees
    }


    void initializeSensors() {
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
            float[] quaternion = new float[4];
            System.arraycopy(event.values, 0, quaternion, 0, 4);
            float[] euler = quaternionToEuler(quaternion);
            float roll = euler[0];
            float pitch = euler[1];
            float yaw = euler[2];


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
}
