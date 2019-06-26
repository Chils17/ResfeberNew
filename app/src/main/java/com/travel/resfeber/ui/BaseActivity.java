package com.travel.resfeber.ui;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.travel.resfeber.R;
import com.travel.resfeber.fragments.BaseFragment;
import com.travel.resfeber.fragments.BookingFragment;
import com.travel.resfeber.helper.AppProgressLoader;

import java.util.Stack;

public class BaseActivity extends AppCompatActivity {
    /**
     * context
     */
    private Context context;

    /**
     * instance of base activity
     */
    private BaseActivity baseActivity;

    /**
     * The Show back message.<br>
     * this is used to check that whether user have to press twice to exit.
     */
    private boolean showBackMessage = true;

    /**
     * The Progress loader.
     */
    private AppProgressLoader progressLoader;

    /**
     * The double back to exit pressed once. It is used to check that user pressed back button twice
     */
    private boolean doubleBackToExitPressedOnce;

    /**
     * The drawer layout, Left navigation drawer.
     */
    private DrawerLayout drawerLayout;

    /**
     * fragment stack to maintain stacks of fragments on activity.
     */
    private Stack<android.support.v4.app.Fragment> fragmentBackStack;

    /**
     * The constant IS_FROM to check from which screen it comes.
     */
    public static final String IS_FROM = "IS_FROM";

    /**
     * The constant CATEGORY_ID to pass category id to detail.
     */
    public static final String CATEGORY_ID = "CATEGORY_ID";
    /**
     * The constant dialogue.
     */
    public Dialog exploreDialogue, premiumMembershipDialogue;

    /**
     * Sets show back message.
     *
     * @param showBackMessage the show back message
     */
    public void setShowBackMessage(boolean showBackMessage) {
        this.showBackMessage = showBackMessage;
    }
    public static String ADDRESS1= "ADDRESS1";
    public static String ADDRESS2= "ADDRESS2";
    public static String COUNTRY= "COUNTRY";
    public static String STATE= "STATE";
    public static String CITY= "CITY";
    public static String ZIPCODE= "ZIPCODE";
    /**
     * Gets drawer layout.
     *
     * @return the drawer layout
     */
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    /**
     * Sets drawer layout.
     *
     * @param drawerLayout the drawer layout
     */
    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        fragmentBackStack = new Stack<>();
    }

    /**
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to show, in tab identified by tag shouldAnimate ->
     * should animate transaction. false when we switch tabs, or adding first fragment to a tab true when when we are pushing more
     * fragment into navigation stack. shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)). false when we are
     * switching tabs (except for the first time) true in all other cases.
     *
     * @param fragment      the fragment
     * @param shouldAnimate the should animate
     * @param shouldAdd     the should add
     */
    public synchronized void pushAddFragments(android.support.v4.app.Fragment fragment, boolean shouldAnimate, boolean shouldAdd) {
       // LogUtils.showInfo(Boolean.toString(shouldAnimate));
        //LogUtils.showInfo(Boolean.toString(shouldAdd));
        if (fragment != null) {
            fragmentBackStack.push(fragment);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            android.support.v4.app.Fragment currentFragmentForAdd = fragmentBackStack.get(fragmentBackStack.size() - 1);
            /**
             *  currentFragmentForAdd check whether it is instance of fragment which has multiple fragments
             *  which will add like tabs with viewpager then it is replaced otherwise add.
             *
             */
            if (currentFragmentForAdd instanceof BookingFragment /*|| currentFragmentForAdd instanceof OrderHistoryFragment*/) {
                ft.replace(R.id.contentFrame, fragment, String.valueOf(fragmentBackStack.size()));
            } else {
                ft.add(R.id.contentFrame, fragment, String.valueOf(fragmentBackStack.size()));
            }
            ft.commit();
            manager.executePendingTransactions();
            if (drawerLayout != null && fragmentBackStack.size() > 1) {
                android.support.v4.app.Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                if (currentFragment instanceof BookingFragment) {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }
        }
    }

 /*   *//**
     * Select the second last fragment in current tab's stack.. which will be shown after the fragment transaction given below
     *
     * @param shouldAnimate the should animate
     *//*
    public synchronized void popFragments(boolean shouldAnimate) {
        Fragment fragment = null;
        if (fragmentBackStack.size() > 1) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 2);
        } else if (!fragmentBackStack.isEmpty()) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 1);
        }
        if (fragment instanceof DashBoardFragment) {
            loadBottomUI(AppConstants.HOME);
        } else if (fragment instanceof RentFragment) {
            loadBottomUI(AppConstants.RENT);
        } else if (fragment instanceof ShopFragment) {
            loadBottomUI(AppConstants.SHOP);
        } else if (fragment instanceof MyAccountFragment) {
            loadBottomUI(AppConstants.ACCOUNT);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                ft.remove(fragmentBackStack.elementAt(fragmentBackStack.size() - 1));
                if (fragmentBackStack.size() > 1) {
                    ft.show(fragment).commit();
                }
            } else {
                ft.replace(R.id.contentFrame, fragment).commit();
            }
            fragmentBackStack.pop();
            manager.executePendingTransactions();
            // Here we checking that whether we have to close navigation drawer on fragments or not.
            if (!fragmentBackStack.isEmpty() && isSlideBarOpen(fragmentBackStack.get(fragmentBackStack.size() - 1)) && drawerLayout != null) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (fragmentBackStack.get(fragmentBackStack.size() - 1) instanceof DashBoardFragment) {
                    DashBoardFragment homeFragment = (DashBoardFragment) fragmentBackStack.get(fragmentBackStack.size() - 1);
                }
            }
        }
    }*/

    /**
     * @return is we have to open navigation drawer.
     */
   /* private boolean isSlideBarOpen(Fragment currentFragment) {
        return currentFragment instanceof BookingFragment;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!getFragments().empty()) {
            android.support.v4.app.Fragment fragment = getFragments().get(getFragments().size() - 1);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    /**
     * Gets fragments.
     *
     * @return the fragments
     */
    public Stack<android.support.v4.app.Fragment> getFragments() {
        return fragmentBackStack;
    }



    /*protected void onPause() {
        if(toast != null)
            toast.cancel();
        super.onPause();
    }*/
    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (fragmentBackStack.size() <= 1) {
            if (showBackMessage) {
                doubleTapOnBackPress();
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        } else {
            if (!((BaseFragment) fragmentBackStack.get(fragmentBackStack.size() - 1)).onFragmentBackPress()) {
                android.support.v4.app.Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                /*if (currentFragment instanceof ShopFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof RentFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof MyAccountFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof WishListFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof MemberShipPlansFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof MyRewardsFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof SearchProductFragment) {
                    loadHomeFragment();
                } else if (currentFragment instanceof DashBoardFragment) {
                    doubleTapOnBackPress();
                } else {
                    popFragments(true);
                }*/
            }
        }
    }

    /**
     * this method load dashboard fragment
     */
    public void loadHomeFragment() {
        //loadBottomUI(AppConstants.HOME);
        getFragments().clear();
        android.support.v4.app.Fragment fragmentToPush = BookingFragment.getFragment(BaseActivity.this);
        pushAddFragments(fragmentToPush, true, true);
    }

    /**
     * method handle for show notification for close the application & Stop app to Close when there is no any remaining Fragment
     * in Stack and User press Back Press
     */
    void doubleTapOnBackPress() {
        if (doubleBackToExitPressedOnce) {
          //  finish();
            finishAffinity();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.click_back_to_exit), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }

}
