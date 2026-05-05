package br.com.raizesdonordeste.model;

import java.time.LocalDate;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private int pontosFidelidade;
    private LocalDate dataConsentimentoLgpd; // null = não consentiu

    public Cliente() {
    }

    public Cliente(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.pontosFidelidade = 0;
        this.dataConsentimentoLgpd = null;
    }

    // Registra o consentimento do cliente para uso dos dados (LGPD)
    public void registrarConsentimento() {
        this.dataConsentimentoLgpd = LocalDate.now();
    }

    // Remove consentimento e apaga dados pessoais sensíveis (direito ao esquecimento)
    public void revogarConsentimento() {
        this.dataConsentimentoLgpd = null;
        this.email = "[removido]";
    }

    public boolean possuiConsentimento() {
        return dataConsentimentoLgpd != null;
    }

    public void adicionarPontos(int pontos) {
        if (pontos <= 0) throw new IllegalArgumentException("Pontos devem ser positivos!");
        this.pontosFidelidade += pontos;
    }

    public void resgatarPontos(int pontos) {
        if (pontos > pontosFidelidade) throw new IllegalStateException("Pontos insuficientes!");
        this.pontosFidelidade -= pontos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public LocalDate getDataConsentimentoLgpd() {
        return dataConsentimentoLgpd;
    }
}
