package es.daniel.buscaminas.lib;

import android.util.DisplayMetrics;

public class ViewHelper {

    int displayWidthInPx;
    private float density;

    public ViewHelper(DisplayMetrics displayMetrics) {
        displayWidthInPx =  displayMetrics.widthPixels;
        density = displayMetrics.density;
    }

    public int dpsToPx(int dps) {
        return Math.round(dps * density);
    }

    public int calcWidth(int minWidthBoxInPx, int marginInPx, int nBoxes) {
        if(displayWidthInPx > (minWidthBoxInPx + marginInPx * 2) * nBoxes) {
            return (int)(displayWidthInPx / nBoxes - marginInPx * 2);
        } else {
            return (int)minWidthBoxInPx;
        }
    }

}
