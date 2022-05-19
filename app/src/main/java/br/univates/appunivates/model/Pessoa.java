package br.univates.appunivates.model;

public class Pessoa {
    private int id;
    private String nome;
    private String telefone;

    public int getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override

    public String toString(){
        return nome;
    }
}