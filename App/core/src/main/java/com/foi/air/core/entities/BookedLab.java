package com.foi.air.core.entities;

public class BookedLab {
    int idAktivnosti;
    String kolegij;
    String danIzvodenja;
    String pocetak;
    String kraj;
    int dozvoljenoIzostanaka;
    String dvorana;
    String tipAktivnosti;
    int kapacitet;
    int brojUpisanih;

    public BookedLab() {
    }

    public BookedLab(int idAktivnosti, String kolegij, String danIzvodenja, String pocetak, String kraj, int dozvoljenoIzostanaka, String dvorana, String tipAktivnosti, int kapacitet, int brojUpisanih) {
        this.idAktivnosti = idAktivnosti;
        this.kolegij = kolegij;
        this.danIzvodenja = danIzvodenja;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.dozvoljenoIzostanaka = dozvoljenoIzostanaka;
        this.dvorana = dvorana;
        this.tipAktivnosti = tipAktivnosti;
        this.kapacitet = kapacitet;
        this.brojUpisanih = brojUpisanih;
    }

    public int getIdAktivnosti() {
        return idAktivnosti;
    }

    public void setIdAktivnosti(int idAktivnosti) {
        this.idAktivnosti = idAktivnosti;
    }

    public String getKolegij() {
        return kolegij;
    }

    public void setKolegij(String kolegij) {
        this.kolegij = kolegij;
    }

    public String getDanIzvodenja() {
        return danIzvodenja;
    }

    public void setDanIzvodenja(String danIzvodenja) {
        this.danIzvodenja = danIzvodenja;
    }

    public String getPocetak() {
        return pocetak;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public int getDozvoljenoIzostanaka() {
        return dozvoljenoIzostanaka;
    }

    public void setDozvoljenoIzostanaka(int dozvoljenoIzostanaka) {
        this.dozvoljenoIzostanaka = dozvoljenoIzostanaka;
    }

    public String getDvorana() {
        return dvorana;
    }

    public void setDvorana(String dvorana) {
        this.dvorana = dvorana;
    }

    public String getTipAktivnosti() {
        return tipAktivnosti;
    }

    public void setTipAktivnosti(String tipAktivnosti) {
        this.tipAktivnosti = tipAktivnosti;
    }

    public int getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(int kapacitet) {
        this.kapacitet = kapacitet;
    }

    public int getBrojUpisanih() {
        return brojUpisanih;
    }

    public void setBrojUpisanih(int brojUpisanih) {
        this.brojUpisanih = brojUpisanih;
    }
}
