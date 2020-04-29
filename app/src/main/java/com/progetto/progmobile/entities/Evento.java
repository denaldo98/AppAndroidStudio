package com.progetto.progmobile.entities;

public class Evento {
    private String nome;
    private String aula;
    private String giorno;
    private String oraInizio;
    private String oraFine;

    public Evento(String nome, String aula, String giorno, String oraInizio, String oraFine) {
        this.nome = nome;
        this.aula = aula;
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public String getNome() {
        return nome;
    }
    public String getAula() { return aula; }
    public String getGiorno() { return giorno; }
    public String getOraInizio() { return oraInizio; }
    public String getOraFine() { return oraFine; }
}
