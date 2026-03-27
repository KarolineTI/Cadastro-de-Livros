package cadastro_livro.cadastro_livro;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LivroController {

    @GetMapping("/")
    public String carregarIndex(Model model) {
        // Adicionamos um atributo vazio apenas para forçar o Thymeleaf a processar a página
        model.addAttribute("mensagem", "O sistema está online!");
        return "index";
    }
}