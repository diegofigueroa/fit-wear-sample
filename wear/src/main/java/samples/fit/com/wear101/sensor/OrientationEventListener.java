package samples.fit.com.wear101.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import samples.fit.com.wear101.CompassDrawer;

public class OrientationEventListener implements SensorEventListener {
    private final CompassDrawer compassDrawer;

    public OrientationEventListener(final CompassDrawer drawer) {
        this.compassDrawer = drawer;
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        float azimuth = event.values[0];
        float pitch = event.values[1];
        float roll = event.values[2];

        Log.d("OrientationEventListener", String.format("azimuth: %f, pitch: %f, roll: %f", azimuth, pitch, roll));

        compassDrawer.update(azimuth);
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        // nothing to do here
    }
}
