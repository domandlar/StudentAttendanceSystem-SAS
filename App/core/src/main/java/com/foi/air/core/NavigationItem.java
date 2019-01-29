package com.foi.air.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import java.util.List;

public interface NavigationItem {
    boolean prisustvo=false;
    public Fragment getFragment();
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave);
    public boolean getData();
}
