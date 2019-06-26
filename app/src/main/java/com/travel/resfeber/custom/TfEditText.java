package com.travel.resfeber.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.travel.resfeber.R;
import com.travel.resfeber.helper.Function;


/**
 * Created by its7 on 11/1/18.
 */

public class TfEditText extends AppCompatEditText {
    private final String TAG = getClass().getName();
    private Context _ctx;
    private boolean isBold;
    private int fTypeValue = 0;

    public TfEditText(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }
    public TfEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TtEditTextSimple, 0, 0);
            try {
                isBold = a.getBoolean(R.styleable.TtEditTextSimple_isBoldEditText, false);

                if (a.hasValue(R.styleable.TtEditTextSimple_fEdittype)) {
                    fTypeValue = a.getInt(R.styleable.TtEditTextSimple_fEdittype, 0);
                }

            } finally {
                a.recycle();
            }

            this._ctx = context;
            init();
        }
    }
    private void init() {
        try {
            setTypeface(Function.getFontType(getContext(), fTypeValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
