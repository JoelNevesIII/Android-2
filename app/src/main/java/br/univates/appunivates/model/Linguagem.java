package br.univates.appunivates.model;

import java.text.Normalizer;

public class Linguagem {
    private int id;
    private String nome;
    private String descricao;
    private int nota;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int notas) {
        this.nota = notas;
    }

    @Override
    public String toString(){
        return nome;
    }
}
