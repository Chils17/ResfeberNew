package com.travel.resfeber.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.travel.resfeber.helper.Function;

/**
 * Created by its7 on 11/1/18.
 */

public class TfButton extends android.support.v7.widget.AppCompatButton {
    private Context _ctx;

    public TfButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    public TfButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    private void init() {
        try {
            // setTypeface(Functions.getLatoFont(_ctx));
            setTypeface(Function.getFontType(_ctx, FontType.Bold.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
