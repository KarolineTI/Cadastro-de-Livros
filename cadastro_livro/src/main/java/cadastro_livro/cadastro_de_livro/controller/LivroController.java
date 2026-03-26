package cadastro_livro.cadastro_de_livro.controller;

import cadastro_livro.cadastro_livro.Livro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller

public class LivroController {

    // Lista para simular o 'banco de dados' em memória
    private List<Livro> livros = new ArrayList<>();
    // Contador para gerar IDs únicos
    private AtomicLong counter = new AtomicLong();

    {
        // Cria alguns livros de exemplo ao iniciar o Controller
        livros.add(new Livro(counter.incrementAndGet(), "O Senhor dos Anéis", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), "A jornada de um hobbit para destruir um anel maligno."));
        livros.add(new Livro(counter.incrementAndGet(), "1984", "George Orwell", LocalDate.of(1949, 6, 8), "Uma distopia sobre vigilância e totalitarismo."));
    }

    // Crud

    // 1. Read
    @GetMapping("/")
    public String paginaInicial(Model model) {
        // Adiciona a lista de livros ao Model
        model.addAttribute("livros", livros);
        // Retorna o nome da View
        return "index.html";
    }

    // 2. Create (Criar) & Update (Atualizar) - Exibir o Formulário
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        // Adiciona um objeto Livro vazio para o Thymeleaf preencher o formulário
        model.addAttribute("livro", new Livro());
        return "cadastro-livro";
    }

    // 3. Update (Atualizar) - Exibir o Formulário de Edição
    @GetMapping("/livros/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        // Busca o livro pelo ID
        Livro livroParaEditar = livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (livroParaEditar != null) {
            // Adiciona o livro encontrado ao Model, preenchendo o formulário
            model.addAttribute("livro", livroParaEditar);
            return "cadastro-livro";
        }
        // Se não encontrar, redireciona para a página inicial
        return "redirect:/";
    }

    // 4. Create e Update
    @PostMapping("/livros")
    public String salvarLivro(@ModelAttribute Livro livro, RedirectAttributes attributes) {

        if (livro.getId() != null) {
            // Lógica para UPDATE (id existe)
            boolean encontrado = false;
            for (int i = 0; i < livros.size(); i++) {
                if (livros.get(i).getId().equals(livro.getId())) {
                    livros.set(i, livro); // Atualiza o objeto na lista
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                attributes.addFlashAttribute("mensagem", "Livro atualizado com sucesso!");
            }
        } else {
            // Lógica para CREATE (id é nulo)
            livro.setId(counter.incrementAndGet()); // Gera novo ID
            livros.add(livro); // Adiciona novo livro à lista
            attributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
        }

        // Redireciona para a página inicial, exibindo a lista atualizada
        return "redirect:/";
    }

    // 5. Delete (Deletar) - Remover um Livro
    @GetMapping("/livros/remover/{id}")
    public String removerLivro(@PathVariable Long id, RedirectAttributes attributes) {
        // Remove o livro da lista usando um lambda
        boolean removido = livros.removeIf(l -> l.getId().equals(id));

        if (removido) {
            attributes.addFlashAttribute("mensagem", "Livro removido com sucesso!");
        } else {
            attributes.addFlashAttribute("erro", "Livro não encontrado.");
        }

        // Redireciona para a página inicial
        return "redirect:/";
    }
}