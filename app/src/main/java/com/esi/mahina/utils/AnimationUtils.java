package com.esi.mahina.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class AnimationUtils {

    /**
     * Adds a touch scale effect to a view (press down, release up)
     */
    public static void addTouchScaleEffect(View view) {
        addTouchScaleEffect(view, 0.97f);
    }

    public static void addTouchScaleEffect(View view, float scaleFactor) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animateScale(v, scaleFactor, 100);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    animateScale(v, 1.0f, 200);
                    break;
            }
            return false; // Don't consume the event
        });
    }

    /**
     * Adds a touch scale effect with elevation change
     */
    public static void addTouchScaleWithElevation(View view) {
        float originalElevation = view.getElevation();
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animateScale(v, 0.97f, 100);
                    animateElevation(v, originalElevation, originalElevation * 0.5f, 100);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    animateScale(v, 1.0f, 200);
                    animateElevation(v, originalElevation * 0.5f, originalElevation, 200);
                    break;
            }
            return false;
        });
    }

    /**
     * Animates scale of a view
     */
    public static void animateScale(View view, float targetScale, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", targetScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", targetScale);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    /**
     * Animates elevation of a view
     */
    public static void animateElevation(View view, float from, float to, long duration) {
        ObjectAnimator elevation = ObjectAnimator.ofFloat(view, "elevation", from, to);
        elevation.setDuration(duration);
        elevation.setInterpolator(new DecelerateInterpolator());
        elevation.start();
    }

    /**
     * Fade in animation with slide up
     */
    public static void fadeInSlideUp(View view, long delay) {
        view.setAlpha(0f);
        view.setTranslationY(50f);
        view.setVisibility(View.VISIBLE);

        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(delay)
                .setInterpolator(new DecelerateInterpolator(2f))
                .start();
    }

    /**
     * Fade in animation
     */
    public static void fadeIn(View view, long duration, long delay) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .setStartDelay(delay)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    /**
     * Scale in with bounce effect
     */
    public static void scaleInBounce(View view, long delay) {
        view.setScaleX(0.5f);
        view.setScaleY(0.5f);
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new OvershootInterpolator(1.5f))
                .start();
    }

    /**
     * Pulse animation for highlighting
     */
    public static void pulse(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.08f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.08f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    /**
     * Continuous gentle pulse for status indicators
     */
    public static ObjectAnimator startContinuousPulse(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.6f, 1f);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        return animator;
    }

    /**
     * Animate a progress bar smoothly
     */
    public static void animateProgress(android.widget.ProgressBar progressBar, int targetProgress, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), targetProgress);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.start();
    }

    /**
     * Staggered animation for multiple views
     */
    public static void staggeredFadeIn(View[] views, long baseDelay, long staggerDelay) {
        for (int i = 0; i < views.length; i++) {
            fadeInSlideUp(views[i], baseDelay + (i * staggerDelay));
        }
    }

    /**
     * Shake animation for errors
     */
    public static void shake(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, 15, -15, 10, -10, 5, -5, 0);
        animator.setDuration(400);
        animator.start();
    }

    /**
     * Rotate animation
     */
    public static void rotate(View view, float fromDegrees, float toDegrees, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", fromDegrees, toDegrees);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * Number count up animation for text
     */
    public static void countUp(android.widget.TextView textView, int from, int to, long duration, String suffix) {
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            textView.setText(value + (suffix != null ? suffix : ""));
        });
        animator.start();
    }
}
