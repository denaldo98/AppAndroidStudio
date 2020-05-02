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
    public Attivita () {
        /*this.nome = "";
        this.data="";
        this.descrizione="";
        this.priorita=1;*/
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPriorita(int priorita) {
        this.priorita = priorita;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(String data) {
        this.data = data;
    }
}
