package com.foi.air.core.entities;

public class Kolegij {
    private int id, semestar;
    private String naziv, studij;

    public Kolegij(int id, String naziv, int semestar, String studij) {
        this.id = id;
        this.naziv = naziv;
        this.semestar = semestar;
        this.studij = studij;
    }

    public int getId() {
        return id;
    }
    public int getSemestar() {
        return semestar;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getStudij() {
        return studij;
    }

}
