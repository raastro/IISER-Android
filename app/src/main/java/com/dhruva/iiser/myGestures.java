package com.dhruva.iiser;

import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashSet;

class myGestures {
    static final int SWIPE_RIGHT_LEFT = 1, SWIPE_LEFT_RIGHT = 2, SWIPE_UP_DOWN = 3, SWIPE_DOWN_UP = 4,
            CUT_LEFT = 5, CUT_RIGHT = 6, CUT_DOWN = 7, CUT_UP = 8, NONE = 0;
    private float[] oldEvent = new float[3];
    private float heightLimit, widthLimit, hScrollSize, vScrollSize, velocityLimit;
    private int screenLimit;

    myGestures() {
        this.heightLimit = 1f / 4;
        this.widthLimit = 3f / 4;
        this.hScrollSize = 1f / 3;
        this.vScrollSize = 1f / 5;
        this.velocityLimit = 3.0f;
        this.screenLimit = 40;
    }

    myGestures(float heightLimit, float widthLimit, float hScrollSize, float vScrollSize, float velocityLimit) {
        this.heightLimit = heightLimit;
        this.widthLimit = widthLimit;
        this.hScrollSize = hScrollSize;
        this.vScrollSize = vScrollSize;
        this.velocityLimit = velocityLimit;
    }

    HashSet<Integer> getGestures(MotionEvent event) {
        HashSet<Integer> results = new HashSet<>();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldEvent[0] = (event.getX());
                oldEvent[1] = (event.getY());
                oldEvent[2] = ((float) event.getEventTime());
                results.add(NONE);
                break;
            case MotionEvent.ACTION_UP:
                if (oldEvent == null) {
                    results.add(NONE);
                    return results;
                }

                float deltaX = event.getX() - oldEvent[0];
                float deltaY = event.getY() - oldEvent[1];
                float deltaT = event.getEventTime() - oldEvent[2];

                Log.d("delx", String.valueOf(deltaX));
                Log.d("dely", String.valueOf(deltaY));
                Log.d("delt", String.valueOf(deltaT));

                //Horizontal swipe check
                if (Math.abs(deltaX / deltaT) > velocityLimit && Math.abs(deltaX) > hScrollSize * getScreenWidth()) {

                    if (oldEvent[0] < widthLimit * getScreenWidth() && deltaX < 0) {
                        // Swiped right to left
                        results.add(SWIPE_RIGHT_LEFT);
                    } else if (oldEvent[1] > (1 - widthLimit) * getScreenWidth() && deltaX > 0) {
                        // Swiped left to right
                        results.add(SWIPE_LEFT_RIGHT);
                    }
                }

                //Vertical swipe check
                if (Math.abs(deltaY / deltaT) > velocityLimit && Math.abs(deltaY) > vScrollSize * getScreenHeight()) {
                    if (oldEvent[1] < heightLimit * getScreenHeight() &&
                            deltaY > vScrollSize * getScreenHeight() &&
                            Math.abs(deltaY / deltaT) > velocityLimit) {
                        // Swiped up to down
                        results.add(SWIPE_UP_DOWN);
                    } else if (oldEvent[1] > (1 - heightLimit) * getScreenHeight() &&
                            deltaY < -vScrollSize * getScreenHeight() &&
                            Math.abs(deltaY / deltaT) > velocityLimit) {
                        // Swiped Down to Up - Do nothing
                        results.add(SWIPE_DOWN_UP);
                    }
                }

                //Cut checks
                if (300 > Math.abs(deltaX) && Math.abs(deltaX) > 50) {
                    if (event.getX() < screenLimit)
                        results.add(CUT_LEFT);
                    else if (event.getX() > getScreenWidth() - screenLimit)
                        results.add(CUT_RIGHT);
                }
                if (300 > Math.abs(deltaY) && Math.abs(deltaY) > 50) {
                    if (event.getY() < screenLimit)
                        results.add(CUT_UP);
                    else if (event.getY() > getScreenHeight() - screenLimit)
                        results.add(CUT_DOWN);
                }

                //Check if no changes
                if (results.size() == 0) {
                    results.add(NONE);
                }
                break;
            default:
                results.add(NONE);
        }
        return results;
    }

    static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
