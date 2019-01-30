package com.foi.air.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.foi.air.core.entities.Dolazak;

import java.util.ArrayList;
import java.util.List;

public interface NavigationItem {
    public Fragment getFragment();
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave);
    public ArrayList<Dolazak> getData();
}
