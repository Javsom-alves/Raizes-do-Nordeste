package br.com.raizesdonordeste.model;

public class Unidade {

    private int id;
    private String nome;
    private String cidade;
    private String endereco;
    private boolean ativa;

    public Unidade() {
    }

    public Unidade(int id, String nome, String cidade, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.endereco = endereco;
        this.ativa = true;
    }

    public int getId() {
        return id;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}