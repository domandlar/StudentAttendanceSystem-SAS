package com.foi.air.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.foi.air.core.entities.Student;

import java.util.List;

public interface NavigationItem {
    public Fragment getFragment();
    public String getName(Context context);
    public Drawable getIcon(Context context);
    public void setData(List<Student> students);
}
