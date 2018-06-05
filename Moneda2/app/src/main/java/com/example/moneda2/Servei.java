package com.example.moneda2;


public class Servei {
    private String servei;
    private String horari;
    private String telefon;

    public Servei(String servei, String horari, String telefon) {
        this.servei = servei;
        this.horari = horari;
        this.telefon = telefon;
    }

    public String getServei() {
        return servei;
    }

    public void setServei(String servei) {
        this.servei = servei;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
