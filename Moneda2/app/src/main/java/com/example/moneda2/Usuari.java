package com.example.moneda2;



public class Usuari {

    private String nom;
    private String cognom;
    private String dni;
    private String correu;
    private String contrasenya;
    private String saldo;

    public Usuari(String nom, String cognom, String dni, String correu, String contrasenya, String saldo){

        this.nom= nom;
        this.cognom=cognom;
        this.dni=dni;
        this.correu=correu;
        this.contrasenya=contrasenya;
        this.saldo=saldo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}

