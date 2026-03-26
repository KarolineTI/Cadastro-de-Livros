package com.livraria.cadastro_livro.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Long id;
    private String nomeUsuario;
    private String nomeCompleto;
    private String nascimento;
    private String email;
    private String senha;
    private List<ItemCompra> compras;


    public Usuario() {
        this.compras = new ArrayList<>();
    }

    public Usuario(Long id, String nomeUsuario, String nomeCompleto, String email, String senha, String nascimento) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.nomeCompleto = nomeCompleto;
        this.nascimento = nascimento;
        this.email = email;
        this.senha = senha;
        this.compras = new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getNascimento() { return nascimento; }
    public void setNascimento(String nascimento) { this.nascimento = nascimento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public void adicionarCompra(Livro livro, int quantidade) {
        this.compras.add(new ItemCompra(livro, quantidade));
    }

    public List<ItemCompra> getCompras() {
        return compras;
    }


    private Cartao cartao;

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

}
