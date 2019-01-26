package com.foi.air.core.entities;

public class Dolazak {
    int tjedanNastave;
    boolean prisustvo;

    public Dolazak(){

    }

    public int getTjedanNastave() {
        return tjedanNastave;
    }

    public void setTjedanNastave(int tjedanNastave) {
        this.tjedanNastave = tjedanNastave;
    }

    public void setPrisustvo(boolean prisustvo) {
        this.prisustvo = prisustvo;
    }

    public boolean isPrisustvo() {
        return prisustvo;
    }
}
