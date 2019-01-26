package com.foi.air.core.entities;

public class TipAktivnosti {
    int id;
    String naziv;

    public TipAktivnosti(int id,String naziv){
        this.id=id;
        this.naziv=naziv;

    }
    public TipAktivnosti(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}

