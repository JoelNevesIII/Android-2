package br.univates.appunivates.model;

public class Nota {
    private int id;
    private String nota;


    public Nota (int id, String nota){
        this.id  = id;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString(){
        return nota;
    }
}
