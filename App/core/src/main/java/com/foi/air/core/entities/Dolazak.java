package com.foi.air.core.entities;

public class Dolazak {
    int idDolazka;
    int tjedanNastave;
    boolean prisustvo;
    int idStudenta;
    int idAktivnosti;

    public Dolazak() {
    }

    public int getIdDolazka() {
        return idDolazka;
    }

    public void setIdDolazka(int idDolazka) {
        this.idDolazka = idDolazka;
    }

    public int getTjedanNastave() {
        return tjedanNastave;
    }

    public void setTjedanNastave(int tjedanNastave) {
        this.tjedanNastave = tjedanNastave;
    }

    public boolean isPrisustvo() {
        return prisustvo;
    }

    public void setPrisustvo(boolean prisustvo) {
        this.prisustvo = prisustvo;
    }

    public int getIdStudenta() {
        return idStudenta;
    }

    public void setIdStudenta(int idStudenta) {
        this.idStudenta = idStudenta;
    }

    public int getIdAktivnosti() {
        return idAktivnosti;
    }

    public void setIdAktivnosti(int idAktivnosti) {
        this.idAktivnosti = idAktivnosti;
    }
}
