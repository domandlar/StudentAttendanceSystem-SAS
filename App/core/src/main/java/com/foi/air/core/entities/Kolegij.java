package com.foi.air.core.entities;

public class Kolegij {

    private int id, semestar;
    private String naziv, studij;


    public Kolegij(int id, String naziv, int semestar, String studij) {
        this.id = id;
        this.semestar = semestar;
        this.naziv = naziv;
        this.studij = studij;
    }
    public Kolegij(int id){

        this.id = id;

    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemestar() {
        return semestar;
    }

    public void setSemestar(int semestar) {
        this.semestar = semestar;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getStudij() {
        return studij;
    }

    public void setStudij(String studij) {
        this.studij = studij;
    }

    @Override
    public String toString() {
        return naziv;
    }

}
