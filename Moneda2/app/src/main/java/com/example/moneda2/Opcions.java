package com.example.moneda2;



public class Opcions {

    private String opcio;
    private int logo;

    public Opcions(String opcio, int logo) {
        this.opcio = opcio;
        this.logo = logo;
    }

    public String getOpcio() {
        return opcio;
    }

    public void setOpcio(String opcio) {
        this.opcio = opcio;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
