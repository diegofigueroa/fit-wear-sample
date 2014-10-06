package samples.fit.com.wear101.activity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import samples.fit.com.wear101.AnimatorRunnable;
import samples.fit.com.wear101.CompassDrawer;
import samples.fit.com.wear101.LongPressListener;
import samples.fit.com.wear101.sensor.OrientationEventListener;
import samples.fit.com.wear101.R;

public class MyActivity extends Activity implements CompassDrawer {
    private TextView label;
    private ImageView image;

    private Sensor mOrientationSensor;
    private SensorManager mSensorManager;
    private OrientationEventListener mOrientationEventListener;

    private DismissOverlayView mDismissOverlayView;
    private GestureDetectorCompat mGestureDetector;

    private float currentDegree = 0f;
    private boolean uiReady = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initSensors();

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                image = (ImageView) stub.findViewById(R.id.compass);
                label = (TextView) stub.findViewById(R.id.label);

                uiReady = true;
            }
        });

        mDismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlayView.setIntroText(R.string.intro_text);
        mDismissOverlayView.showIntroIfNecessary();

        mGestureDetector = new GestureDetectorCompat(this, new LongPressListener(mDismissOverlayView));
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(mOrientationEventListener, mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(mOrientationEventListener);
    }

    @Override
    public void update(float degree) {
        if (uiReady) {
            rotateCompass(degree);
            //rotateCompassAsync(degree);
            updateLabel(degree);
        }
    }

    private void initSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mOrientationEventListener = new OrientationEventListener(this);

        final List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor : deviceSensors) {
            Log.i("wear 101", sensor.getName());
        }
    }

    private void rotateCompass(float degree) {
        // reverse turn degree degrees
        degree = -degree;

        final RotateAnimation ra = new RotateAnimation(
                currentDegree, degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        // save the position for next update
        currentDegree = degree;

        // how long the animation will take place
        ra.setDuration(200);

        // fix the image after animation ends
        ra.setFillAfter(true);

        // start the animation
        image.startAnimation(ra);
    }

    private void rotateCompassAsync(float degree) {
        // reverse turn degree degrees
        degree = -degree;
        image.post(new AnimatorRunnable(image, currentDegree, degree));
        // save the position for next update
        currentDegree = degree;
    }

    private void updateLabel(float degree) {
        int roundedDegree = Math.round(degree);
        //   0 -  90 -> N
        //  90 - 180 -> E
        // 180 - 270 -> S
        // 270 - 360 -> W
        String direction;
        switch (roundedDegree / 90) {
            case 0:
                direction = "N";
                break;
            case 1:
                direction = "E";
                break;
            case 2:
                direction = "S";
                break;
            default:
                direction = "W";
        }

        label.setText(Float.toString(roundedDegree) + "°" + direction);
    }
}
