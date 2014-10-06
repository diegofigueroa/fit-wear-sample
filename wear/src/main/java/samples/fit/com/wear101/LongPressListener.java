package samples.fit.com.wear101;

import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class LongPressListener extends GestureDetector.SimpleOnGestureListener {
    private final DismissOverlayView mDismissOverlayView;

    public LongPressListener(final DismissOverlayView mDismissOverlayView) {
        this.mDismissOverlayView = mDismissOverlayView;
    }

    @Override
    public void onLongPress(final MotionEvent event) {
        mDismissOverlayView.show();
    }
}
