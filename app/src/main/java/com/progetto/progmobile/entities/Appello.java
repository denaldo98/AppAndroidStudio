package com.progetto.progmobile.entities;

public class Appello {
    private String materia;
    //private String data;
    private int anno, mese, giorno;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    /*public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }*/

    public Appello(String materia, int anno, int mese, int giorno) {
        this.materia = materia;
        this.anno = anno;
        this.mese = mese;
        this.giorno = giorno;
    }

    public Appello() {
    }
}
