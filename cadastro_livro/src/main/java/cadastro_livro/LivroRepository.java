package cadastro_livro.cadastro_livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Não precisa escrever nada aqui dentro!
    // O JpaRepository já vem com os comandos:
    // save(), findAll(), findById(), deleteById() prontos.
}