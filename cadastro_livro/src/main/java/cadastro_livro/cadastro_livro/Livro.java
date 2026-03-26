package cadastro_livro.cadastro_livro;
import java.time.LocalDate;
import org.springframework.cglib.core.Local;

public class Livro {

    private Long id;
    private String nomeDoLivro;
    private String nomeDoAutor;
    private LocalDate dataDeLancamento;
    private String sinopse;

    // Construtor
    public Livro() {
    }

    // Construtor com Argumentos
    public Livro(Long id, String nomeDoLivro, String nomeDoAutor, LocalDate dataDeLancamento, String sinopse) {
        this.id = id;
        this.nomeDoLivro = nomeDoLivro;
        this.nomeDoAutor = nomeDoAutor;
        this.dataDeLancamento = dataDeLancamento;
        this.sinopse = sinopse;
    }

    // Getters e Setters (métodos public para ler e alterar atributos private)

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNomeDoLivro() {return nomeDoLivro;}

    public void setNomeDoLivro(String nomeDoLivro) {this.nomeDoLivro = nomeDoLivro;}

    public String getNomeDoAutor() {
        return nomeDoAutor;
    }

    public void setNomeDoAutor(String nomeDoAutor) {
        this.nomeDoAutor = nomeDoAutor;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}