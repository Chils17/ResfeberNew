package com.travel.resfeber.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.travel.resfeber.helper.AppProgressLoader;
import com.travel.resfeber.ui.BaseActivity;

public class BaseFragment extends android.support.v4.app.Fragment {
    /**
     * The Progress loader.
     */
    protected AppProgressLoader progressLoader;
    /**
     * The View.
     */
    protected View view;

    /**
     * The Activity.
     */
    private BaseActivity baseActivity;
    /**
     * Gets base activity.
     *
     * @return the base activity
     */
    public BaseActivity getBaseActivity()
    {
        return baseActivity;
    }

    /**
     * The constant IS_FROM to check from which screen it comes.
     */
    public static final String  IS_FROM = "IS_FROM";

    /**
     * The constant CATEGORY_ID to pass category id to detail.
     */
    public static final String CATEGORY_ID = "CATEGORY_ID";

    /**
     * Sets base activity.
     *
     * @param baseActivity the base activity
     */
    public void setBaseActivity(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }
    /**
     * On fragment back press boolean.
     *
     * @return the boolean
     */
    public boolean onFragmentBackPress()
    {
        return false;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    /**
     * This method is used to show full screen progress
     */
    public void showProgress()
    {
        progressLoader = new AppProgressLoader(baseActivity);
        progressLoader.show(baseActivity, null, false);
        progressLoader.getWindow().setGravity(Gravity.CENTER);
    }

    /**
     * This method is used to close full screen progress
     */
    public void closeProgress()
    {
        if (progressLoader != null && progressLoader.isShowing())
        {
            progressLoader.dismiss();
        }
    }
    /**
     * This method is used to set focus a edit text
     *
     * @param editText the edit text
     */
    protected void requestFocus(EditText editText)
    {
        if (editText != null)
        {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
