package com.progetto.progmobile.entities;

public class Attivita {

    private String nome;
    private int priorita;
    private String descrizione;
    private String data;

    public Attivita(String nome, int priorita, String descrizione, String data) {
        this.nome = nome;
        this.priorita = priorita;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public int getPriorita() {
        return priorita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() { return data;}

}
