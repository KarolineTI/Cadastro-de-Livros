package com.livraria.cadastro_livro.model;

public class ItemCompra {
    private Livro livro;
    private int quantidade;

    public ItemCompra() {}

    public ItemCompra(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(int quantidade) { this.quantidade += quantidade;}



}
