package com.progetto.progmobile.entities;

public class Appunto {
    String titolo;
    String testo;

    public Appunto () {}

    public Appunto(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
