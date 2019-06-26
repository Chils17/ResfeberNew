package com.travel.resfeber.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.travel.resfeber.R;

public class AppProgressLoader extends Dialog {
    /**
     * parametrized constructor
     *
     * @param context Context of application / Activity
     */
    public AppProgressLoader(Context context) {
        super(context);
    }

    /**
     * Display custom dialog
     *
     * @param context    Context of application / Activity
     * @param title      title of progress
     * @param cancelable check that is progress dialog is cancelable
     */
    public void show(Context context, CharSequence title, boolean cancelable) {
        show(context, title, cancelable, null);
    }

    /**
     * Display custom dialog
     *
     * @param context        Context of application / Activity
     * @param title          title of progress
     * @param cancelable     check that is progress dialog is cancelable
     * @param cancelListener listener for click event
     */
    public void show(Context context, CharSequence title, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        this.setTitle(title);
        this.setCancelable(cancelable);
        this.setOnCancelListener(cancelListener);
        View view = getLayoutInflater().inflate(R.layout.app_progress_loader, null);

        /** The next line will add the ProgressBar to the dialog. */
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addContentView(view, layoutParams);

        this.show();
    }
}
