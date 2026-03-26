package com.livraria.cadastro_livro.model;

public class Livro {
    private Long id;
    private String titulo;
    private String autor;
    private int ano;
    private String descricao;
    private String imagemUrl;
    private String categoriaLivro;
    private double valor;
    private int quantidadeDisponivel;

    public Livro() {}

    public Livro(Long id, String titulo, String autor, int ano, String descricao, String imagemUrl, String categoriaLivro, double valor, int quantidadeDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.categoriaLivro = categoriaLivro;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Livro(Long id, String titulo, String autor, int ano, String descricao, String imagemUrl, double valor, int quantidadeDisponivel) {
        this(id, titulo, autor, ano, descricao, imagemUrl, "", valor, quantidadeDisponivel);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getCategoriaLivro() { return categoriaLivro; }
    public void setCategoriaLivro(String categoriaLivro) { this.categoriaLivro = categoriaLivro; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
}
