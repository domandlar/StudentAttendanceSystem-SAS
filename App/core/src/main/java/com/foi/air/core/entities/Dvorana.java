package com.foi.air.core.entities;

public class Dvorana {
    int idDvorane;
    String nazivDvorane;
    int kapacitetDvorane;
    String tipDvorane;

    public Dvorana(int idDvorane, String nazivDvorane, int kapacitetDvorane) {
        this.idDvorane = idDvorane;
        this.nazivDvorane = nazivDvorane;
        this.kapacitetDvorane = kapacitetDvorane;
    }

    public int getIdDvorane() {
        return idDvorane;
    }

    public void setIdDvorane(int idDvorane) {
        this.idDvorane = idDvorane;
    }

    public String getNazivDvorane() {
        return nazivDvorane;
    }

    public void setNazivDvorane(String nazivDvorane) {
        this.nazivDvorane = nazivDvorane;
    }

    public int getKapacitetDvorane() {
        return kapacitetDvorane;
    }

    public void setKapacitetDvorane(int kapacitetDvorane) {
        this.kapacitetDvorane = kapacitetDvorane;
    }

    public String getTipDvorane() {
        return tipDvorane;
    }

    public void setTipDvorane(String tipDvorane) {
        this.tipDvorane = tipDvorane;
    }
    public String toString() {
        return nazivDvorane;
    }
}
