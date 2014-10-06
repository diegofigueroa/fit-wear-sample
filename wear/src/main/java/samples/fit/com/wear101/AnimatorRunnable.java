package samples.fit.com.wear101;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class AnimatorRunnable implements Runnable {
    private final ImageView image;
    private final float previousDegree;
    private final float currentDegree;

    public AnimatorRunnable(final ImageView image, float previousDegree, float currentDegree) {
        this.image = image;
        this.previousDegree = previousDegree;
        this.currentDegree = currentDegree;
    }

    @Override
    public void run() {
        final RotateAnimation ra = new RotateAnimation(
                previousDegree, currentDegree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        // how long the animation will take place
        ra.setDuration(200);

        // fix the image after animation ends
        ra.setFillAfter(true);

        // start the animation
        image.startAnimation(ra);
    }
}
