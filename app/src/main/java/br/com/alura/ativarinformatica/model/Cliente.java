package br.com.alura.ativarinformatica.model;

import android.text.Editable;

import java.io.Serializable;

/**
 * Created by rafael on 04/12/17.
 */

public class Cliente implements Serializable {
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String site;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString()  {
        return getNome();
    }
}
