package com.progetto.progmobile.entities;

public class Corso {
    private String nome;
    private String nomeProfessore;
    private int numeroCFU;
    private String emailProfessore;
    private Appunto[] appunti;

    public Corso (String nome, String nomeProfessore, int numeroCFU, String emailProfessore, Appunto[] appunti){
        this.nome = nome;
        this.nomeProfessore = nomeProfessore;
        this.numeroCFU = numeroCFU;
        this.emailProfessore = emailProfessore;
        this.appunti = appunti;
    }

    public String getNomeProfessore() {
        return nomeProfessore;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroCFU() {
        return numeroCFU;
    }

    public String getEmailProfessore() {
        return emailProfessore;
    }

    public Appunto[] getAppunti() {
        return appunti;
    }
}
