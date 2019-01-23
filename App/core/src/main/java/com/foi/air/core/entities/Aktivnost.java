package com.foi.air.core.entities;

public class Aktivnost {
    int idAktivnosti;
    String kolegij;
    String danIzvodenja;
    String pocetak;
    String kraj;
    int dozvoljenoIzostanaka;
    String dvorana;
    String tipAktivnosti;
    String pocetakUpisa;
    String krajUpisa;



    public Aktivnost(String tipAktivnosti) {
        this.tipAktivnosti = tipAktivnosti;
    }

    public Aktivnost(int idAktivnosti, String kolegij, String danIzvodenja, String pocetak, String kraj, int dozvoljenoIzostanaka, String dvorana, String tipAktivnosti, String pocetakUpisa, String krajUpisa) {
        this.idAktivnosti = idAktivnosti;
        this.kolegij = kolegij;
        this.danIzvodenja = danIzvodenja;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.dozvoljenoIzostanaka = dozvoljenoIzostanaka;
        this.dvorana = dvorana;
        this.tipAktivnosti = tipAktivnosti;
        this.pocetakUpisa = pocetakUpisa;
        this.krajUpisa = krajUpisa;
    }

    public Aktivnost(){

    }

    public String getKolegij() {
        return kolegij;
    }

    public void setKolegij(String kolegij) {
        this.kolegij = kolegij;
    }

    public int getIdAktivnosti() {
        return idAktivnosti;
    }

    public void setIdAktivnosti(int idAktivnosti) {
        this.idAktivnosti = idAktivnosti;
    }

    public int getDozvoljenoIzostanaka() {
        return dozvoljenoIzostanaka;
    }

    public void setDozvoljenoIzostanaka(int dozvoljenoIzostanaka) {
        this.dozvoljenoIzostanaka = dozvoljenoIzostanaka;
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

    public String getDanIzvodenja() {
        return danIzvodenja;
    }

    public void setDanIzvodenja(String danIzvodenja) {
        this.danIzvodenja = danIzvodenja;
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

    public String getPocetakUpisa() {
        return pocetakUpisa;
    }

    public void setPocetakUpisa(String pocetakUpisa) {
        this.pocetakUpisa = pocetakUpisa;
    }

    public String getKrajUpisa() {
        return krajUpisa;
    }

    public void setKrajUpisa(String krajUpisa) {
        this.krajUpisa = krajUpisa;
    }
}
