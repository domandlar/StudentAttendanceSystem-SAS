package com.foi.air.core.entities;

public class AktivnostiProfesora {
    private int idAktivnosti;
    private String pocetak;
    private String kraj;
    private String danIzvodenja;
    private String dvorana;
    private int idTipAktivnosti;
    private String nazivTipaAktivnosti;
    private String nazivKolegija;

    public AktivnostiProfesora(int idAktivnosti, String pocetak, String kraj, String danIzvodenja, String dvorana, int idTipAktivnosti, String nazivTipaAktivnosti, String nazivKolegija) {
        this.idAktivnosti = idAktivnosti;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.danIzvodenja = danIzvodenja;
        this.dvorana = dvorana;
        this.idTipAktivnosti = idTipAktivnosti;
        this.nazivTipaAktivnosti=nazivTipaAktivnosti;
        this.nazivKolegija = nazivKolegija;
    }

    public int getIdAktivnosti() {
        return idAktivnosti;
    }

    public void setIdAktivnosti(int idAktivnosti) {
        this.idAktivnosti = idAktivnosti;
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

    public int getIdTipAktivnosti() {
        return idTipAktivnosti;
    }

    public void setIdTipAktivnosti(int idTipAktivnosti) {
        this.idTipAktivnosti = idTipAktivnosti;
    }

    public String getNazivTipaAktivnosti() {
        return nazivTipaAktivnosti;
    }

    public void setNazivTipaAktivnosti(String nazivTipaAktivnosti) {
        nazivTipaAktivnosti = nazivTipaAktivnosti;
    }

    public String getNazivKolegija() {
        return nazivKolegija;
    }

    public void setNazivKolegija(String nazivKolegija) {
        nazivKolegija = nazivKolegija;
    }
    @Override
    public String toString() {
        return nazivKolegija + " - " + nazivTipaAktivnosti + "(" + pocetak + ")";
    }
}
