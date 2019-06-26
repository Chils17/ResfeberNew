package com.travel.resfeber.helper;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by its7 on 11/1/18.
 */

public class ProgressBarHelper implements ProgressListener {
    private ProgressDialog dialog;

    public ProgressBarHelper(Context context, boolean isCancelable) {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
        dialog.setMessage("Please wait...");
    }
    @Override
    public void showProgressDialog() {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
