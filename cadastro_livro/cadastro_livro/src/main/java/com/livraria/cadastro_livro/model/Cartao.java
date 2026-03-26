package com.livraria.cadastro_livro.model;

public class Cartao {
    private String nomeDoTitular;
    private String numeroDoCartao;
    private String dataVencimento;
    private String codigoSeguranca;

    public Cartao() {}

    public Cartao(String nomeDoTitular, String numeroDoCartao, String dataVencimento, String codigoSeguranca) {
        this.nomeDoTitular = nomeDoTitular;
        this.numeroDoCartao = numeroDoCartao;
        this.dataVencimento = dataVencimento;
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getNomeDoTitular() {
        return nomeDoTitular;
    }

    public void setNomeDoTitular(String nomeDoTitular) {
        this.nomeDoTitular = nomeDoTitular;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }
}
