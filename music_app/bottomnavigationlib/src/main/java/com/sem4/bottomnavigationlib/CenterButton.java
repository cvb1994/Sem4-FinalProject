package com.sem4.bottomnavigationlib;

import android.content.Context;
import android.view.MotionEvent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

class CentreButton extends FloatingActionButton {

    public CentreButton(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result = super.onTouchEvent(ev);
        if (!result) {
            if(ev.getAction() == MotionEvent.ACTION_UP) {
                cancelLongPress();
            }
            setPressed(false);
        }
        return result;
    }
}
