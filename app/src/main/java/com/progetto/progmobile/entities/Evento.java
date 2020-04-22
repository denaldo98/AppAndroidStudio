package com.progetto.progmobile.entities;

public class Evento {
    private String nome;
    private String ora;

    public Evento(String nome, String ora) {
        this.nome = nome;
        this.ora = ora;
    }

    public String getNome() {
        return nome;
    }
    public String getOra() { return ora;}
}
