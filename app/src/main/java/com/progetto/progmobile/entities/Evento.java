package com.progetto.progmobile.entities;

public class Evento {
    private String nome;
    private String aula;

    private String oraInizio;
    private String oraFine;

    public Evento () {}

    public Evento(String nome, String aula, String oraInizio, String oraFine) {
        this.nome = nome;
        this.aula = aula;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public String getNome() {
        return nome;
    }
    public void setNome() {this.nome = nome;}
    public String getAula() { return aula; }
    public void setAula(String aula) {
        this.aula = aula;
    }


    public String getOraInizio() { return oraInizio; }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraFine() { return oraFine; }

    public void setOraFine(String oraFine) {
        this.oraFine = oraFine;
    }
}
