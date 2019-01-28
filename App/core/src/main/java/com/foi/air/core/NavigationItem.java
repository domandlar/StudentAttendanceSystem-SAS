package com.foi.air.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import java.util.List;

public interface NavigationItem {
    public Fragment getFragment();
    public void setData();
}
