package com.foi.air.core.entities;

public class Student {
    int idStudenta;
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;

    public Student(String email, String lozinka) {
        this.email = email;
        this.lozinka = lozinka;
    }
    public Student(){

    }

    public Student(int idStudenta) {
        this.idStudenta = idStudenta;
    }

    public int getIdStudenta() {
        return idStudenta;
    }

    public void setIdStudenta(int idStudenta) {
        this.idStudenta = idStudenta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
