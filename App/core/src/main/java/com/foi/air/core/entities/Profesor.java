package com.foi.air.core.entities;

public class Profesor {
    int idProfesora;
    private String email;
    private String lozinka;

    public Profesor(String email, String lozinka) {
        this.email = email;
        this.lozinka = lozinka;
    }
    public Profesor(int id) {
        this.idProfesora = id;
    }

    public int getIdProfesora() {
        return idProfesora;
    }

    public void setIdProfesora(int idProfesora) {
        this.idProfesora = idProfesora;
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
}
