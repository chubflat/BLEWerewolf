package com.example.kazuaki.blewerewolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Kazuaki on 2016/01/10.
 */
public class CustomView extends View {

    private Bitmap backgroundImg = null;
    private Bitmap roleImg = null;
    private int timer;
    private Bitmap timerFrame = null;


    public CustomView(Context context) {
        super(context);
        setFocusable(true);
    }
}
