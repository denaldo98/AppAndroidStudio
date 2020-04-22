package com.progetto.progmobile.entities;

public class Corso {
    private String nome;
    private String nomeProfessore;
    private int numeroCFU;
    private String emailProfessore;
    private String[] Appunti;

    public Corso (String nome, String nomeProfessore, int numeroCFU, String emailProfessore, String[] Appunti){
        this.nome = nome;
        this.nomeProfessore = nomeProfessore;
        this.numeroCFU = numeroCFU;
        this.emailProfessore = emailProfessore;
        this.Appunti = Appunti;
    }

}
