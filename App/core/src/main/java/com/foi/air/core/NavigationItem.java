package com.foi.air.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.foi.air.core.entities.Dolazak;
import com.foi.air.core.entities.Student;

import java.util.ArrayList;
import java.util.List;

public interface NavigationItem {
    public void setData(int idAktivnosti, int idUloge, int tjedanNastave);
    public ArrayList<Dolazak> getData();
    public interface OnCallbackReceived{
        public void Update();
    };
}
