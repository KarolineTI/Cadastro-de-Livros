    package com.livraria.cadastro_livro.controller;

    import com.livraria.cadastro_livro.model.ItemCompra;
    import com.livraria.cadastro_livro.model.Livro;
    import com.livraria.cadastro_livro.model.Usuario;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import com.livraria.cadastro_livro.model.Cartao;


    import java.util.*;
    import java.util.concurrent.atomic.AtomicLong;
    import java.util.stream.Collectors;

    @Controller
    @RequestMapping("/")
    public class LivroController {

        private static final List<Livro> livros = new ArrayList<>();
        private static final List<Usuario> usuarios = new ArrayList<>();
        private static final AtomicLong contadorLivros = new AtomicLong();
        private static final AtomicLong contadorUsuarios = new AtomicLong(1);

        // Usuário "logado" em memória
        private static Usuario usuarioLogado = null;

        static {
            // Usuário padrão
            Usuario admin = new Usuario(
                    contadorUsuarios.get(),
                    "Arthur",
                    "Arthur Gomes da Silva Cruz",
                    "arthur@gmail.com",
                    "123456",
                    "30/01/2006"
            );
            usuarios.add(admin);

            // Livros que eua dicionei para compra e exemplo
            livros.add(new Livro(contadorLivros.incrementAndGet(), "O Senhor dos Anéis", "J.R.R. Tolkien", 1954,
                    "Uma aventura épica na Terra Média.",
                    "https://m.media-amazon.com/images/I/A1u+2fY5yTL._SY385_.jpg",
                    "Fantasia", 124.50, 20));

            livros.add(new Livro(contadorLivros.incrementAndGet(), "1984", "George Orwell", 1949,
                    "Um futuro distópico sob vigilância constante.",
                    "https://m.media-amazon.com/images/I/819js3EQwbL._SY385_.jpg",
                    "Distopia", 50.06, 30));

            admin.adicionarCompra(livros.get(0), 1);
            admin.adicionarCompra(livros.get(1), 2);
        }

        // Página inicial
        @GetMapping("/index")
        public String homeIndex(Model model) {
            if (usuarioLogado == null) {
                usuarioLogado = new Usuario(0L, "Visitante", "Visitante", "visitante@exemplo.com", "", "15/02/2006");
            }
            model.addAttribute("usuario", usuarioLogado);
            model.addAttribute("livros", new ArrayList<>(livros));
            return "index";
        }

        // Página "Esqueci a Senha"
        @GetMapping("/esqueci-senha")
        public String esqueciSenha(Model model) {
            model.addAttribute("usuario", new Usuario());
            return "esqueciSenha";
        }

        // Processar redefinição de senha
        @PostMapping("/salvar-nova-senha")
        public String salvarNovaSenha(@RequestParam String email,
                                      @RequestParam String novaSenha,
                                      @RequestParam String confirmarNovaSenha,
                                      RedirectAttributes redirect) {

            // Verifica se as senhas coincidem
            if (!novaSenha.equals(confirmarNovaSenha)) {
                redirect.addFlashAttribute("erro", "As senhas não coincidem!");
                return "redirect:/esqueci-senha";
            }

            // Verifica se o email existe
            Optional<Usuario> usuarioExistente = usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();

            if (usuarioExistente.isEmpty()) {
                redirect.addFlashAttribute("erro", "Nenhuma conta encontrada com este email!");
                return "redirect:/esqueci-senha";
            }

            // Atualiza a senha do usuário
            Usuario usuario = usuarioExistente.get();
            usuario.setSenha(novaSenha);

            redirect.addFlashAttribute("mensagem", "Senha alterada com sucesso! Faça login novamente.");
            return "redirect:/login";
        }


        // Página de login
        @GetMapping("/login")
        public String login() {
            return "login";
        }

        // Página sobre nos
        @GetMapping("/sobreNos")
        public String sobreNos() {
            return "sobreNos";
        }

        @GetMapping("/livrosGerais")
        public String livrosGerais() {
            return "livrosGerais";
        }


        // Processar login
        @PostMapping("/login")
        public String processarLogin(@RequestParam String email,
                                     @RequestParam String senha,
                                     RedirectAttributes redirect) {

            // Verifica se o email existe
            Optional<Usuario> usuarioPorEmail = usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();

            if (usuarioPorEmail.isEmpty()) {
                // Nenhum usuário com esse e-mail
                redirect.addFlashAttribute("erro", "Nenhuma conta encontrada com este email. Cadastre-se primeiro!");
                return "redirect:/login";
            }

            // Se o e-mail existe mas a senha está errada
            Usuario usuario = usuarioPorEmail.get();
            if (!usuario.getSenha().equals(senha)) {
                redirect.addFlashAttribute("erro", "Senha incorreta! Tente novamente.");
                return "redirect:/login";
            }

            // Se chegou aqui, login foi bem-sucedido
            usuarioLogado = usuario;
            redirect.addFlashAttribute("mensagem", "Login realizado com sucesso!");
            return "redirect:/index";
        }


        // Processar cadastro
        @PostMapping("/cadastro")
        public String cadastrar(@RequestParam String nomeUsuario,
                                @RequestParam String nomeCompleto,
                                @RequestParam String email,
                                @RequestParam String senha,
                                @RequestParam(required = false) String confirmarSenha,
                                @RequestParam String nascimento,
                                RedirectAttributes redirect) {

            // Verifica se as senhas coincidem
            if (confirmarSenha == null || !senha.equals(confirmarSenha)) {
                redirect.addFlashAttribute("erro", "As senhas não coincidem!");
                return "redirect:/login";
            }

            boolean jaExiste = usuarios.stream()
                    .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

            if (jaExiste) {
                redirect.addFlashAttribute("erro", "Já existe uma conta com esse email!");
                return "redirect:/login";
            }

            Usuario novo = new Usuario(contadorUsuarios.incrementAndGet(), nomeUsuario, nomeCompleto, email, senha,nascimento);
            usuarios.add(novo);

            redirect.addFlashAttribute("mensagem", "Cadastro realizado com sucesso! Faça login.");
            return "redirect:/login";
        }





        @GetMapping("/perfil")
        public String perfil(Model model, RedirectAttributes redirect) {
            if (usuarioLogado == null) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para ver o perfil!");
                return "redirect:/login";
            }

            model.addAttribute("usuario", usuarioLogado);

            double totalGasto = usuarioLogado.getCompras().stream()
                    .mapToDouble(ic -> ic.getLivro().getValor() * ic.getQuantidade())
                    .sum();
            model.addAttribute("totalGasto", totalGasto);

            return "perfil";
        }



        // Logout (sair da conta)
        @GetMapping("/logout")
        public String logout(RedirectAttributes redirect) {
            usuarioLogado = null;
            redirect.addFlashAttribute("mensagem", "Você saiu da conta.");
            return "redirect:/login";
        }

        // Comprar livro
        @GetMapping("/comprar/{id}")
        public String comprarLivro(@PathVariable Long id,
                                   @RequestParam(defaultValue = "1") int qty,
                                   RedirectAttributes redirect) {

            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para comprar!");
                return "redirect:/login";
            }

            // Verifica se o usuário tem um cartão cadastrado
            if (usuarioLogado.getCartao() == null) {
                redirect.addFlashAttribute("erro", "Você precisa cadastrar um cartão antes de comprar!");
                return "redirect:/cadastrar-cartao";
            }


            Livro livroComprado = livros.stream()
                    .filter(l -> l.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (livroComprado == null) {
                redirect.addFlashAttribute("erro", "Livro não encontrado!");
                return "redirect:/";
            }

            // Limita a quantidade à disponível
            int quantidadeFinal = Math.min(qty, livroComprado.getQuantidadeDisponivel());

            if (quantidadeFinal <= 0) {
                redirect.addFlashAttribute("erro", "Quantidade indisponível!");
                return "redirect:/livro/" + id;
            }


            // Atualiza a quantidade disponível do livro
            livroComprado.setQuantidadeDisponivel(livroComprado.getQuantidadeDisponivel() - quantidadeFinal);

            // Verifica se já comprou o mesmo livro
            Optional<ItemCompra> itemExistente = usuarioLogado.getCompras().stream()
                    .filter(ic -> ic.getLivro().getId().equals(livroComprado.getId()))
                    .findFirst();

            if (itemExistente.isPresent()) {
                itemExistente.get().adicionarQuantidade(quantidadeFinal);
                redirect.addFlashAttribute("mensagem", "Você comprou mais " + quantidadeFinal + " unidade(s) deste livro!");
            } else {
                usuarioLogado.adicionarCompra(livroComprado, quantidadeFinal);
                redirect.addFlashAttribute("mensagem", "Compra realizada com sucesso!");
            }

            return "redirect:/perfil";
        }

        // Listar livros com busca
        @GetMapping
        public String listarLivros(Model model,
                                   @RequestParam(value = "busca", required = false) String busca) {
            List<Livro> livrosExibidos;

            if (busca != null && !busca.trim().isEmpty()) {
                String buscaLower = busca.toLowerCase();
                livrosExibidos = livros.stream()
                        .filter(l -> l.getTitulo().toLowerCase().contains(buscaLower)
                                || l.getAutor().toLowerCase().contains(buscaLower)
                                || l.getCategoriaLivro().toLowerCase().contains(buscaLower))
                        .collect(Collectors.toList());
            } else {
                livrosExibidos = new ArrayList<>(livros);
            }

            model.addAttribute("livros", livrosExibidos);
            model.addAttribute("busca", busca);
            return "visualizar_livro";
        }

        // Listar usuario com busca
        @GetMapping("/usuarios")
        public String listarUsuarios(Model model,
                                     @RequestParam(value = "busca", required = false) String busca) {
            List<Usuario> usuariosExibidos;

            if (busca != null && !busca.trim().isEmpty()) {
                String buscaLower = busca.toLowerCase();
                usuariosExibidos = usuarios.stream()
                        .filter(u -> u.getNomeUsuario().toLowerCase().contains(buscaLower)
                                || u.getNomeCompleto().toLowerCase().contains(buscaLower)
                                || u.getEmail().toLowerCase().contains(buscaLower))
                        .collect(Collectors.toList());
            } else {
                usuariosExibidos = new ArrayList<>(usuarios);
            }

            model.addAttribute("usuarios", usuariosExibidos);
            model.addAttribute("busca", busca);
            return "visualizarUsuarios";
        }


        // 🔹 Formulário de cadastro de livro
        @GetMapping("/cadastro-livro")
        public String exibirFormularioCadastro(Model model) {
            model.addAttribute("livro", new Livro());
            return "cadastro-livro";
        }



        // 🔹 Visualizar detalhes do livro
        @GetMapping("/livro/{id}")
        public String visualizarLivro(@PathVariable Long id, Model model, RedirectAttributes redirect) {
            Livro livro = livros.stream()
                    .filter(l -> l.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (livro == null) {
                redirect.addFlashAttribute("erro", "Livro não encontrado!");
                return "redirect:/index";
            }

            model.addAttribute("livro", livro);
            return "visualizarLivroCompra";
        }

        // Editar livro existente
        @GetMapping("/editar/{id}")
        public String editarLivro(@PathVariable Long id, Model model, RedirectAttributes redirect) {
            Livro livro = livros.stream()
                    .filter(l -> l.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (livro == null) {
                redirect.addFlashAttribute("erro", "Livro não encontrado!");
                return "redirect:/";
            }

            model.addAttribute("livro", livro);
            return "cadastro-livro";
        }

        // Exibir formulário de edição do usuário logado
        @GetMapping("/editar-usuario")
        public String editarUsuario(Model model, RedirectAttributes redirect) {
            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para editar o perfil!");
                return "redirect:/login";
            }

            model.addAttribute("usuario", usuarioLogado);
            return "editarUsuario";
        }

        // Salvar alterações do usuário logado
        @PostMapping("/salvar-usuario")
        public String salvarUsuario(@ModelAttribute Usuario usuarioAtualizado, RedirectAttributes redirect) {
            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para editar o perfil!");
                return "redirect:/login";
            }

            // Atualiza os dados do usuário logado
            usuarioLogado.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
            usuarioLogado.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
            usuarioLogado.setEmail(usuarioAtualizado.getEmail());
            usuarioLogado.setSenha(usuarioAtualizado.getSenha());
            usuarioLogado.setNascimento(usuarioAtualizado.getNascimento());

            // Atualiza também na lista de usuários
            usuarios.replaceAll(u -> u.getId().equals(usuarioLogado.getId()) ? usuarioLogado : u);

            redirect.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
            return "redirect:/perfil";
        }



        // Salvar livro (novo ou editado)
        @PostMapping("/salvar")
        public String salvar(@ModelAttribute Livro livro, RedirectAttributes redirect) {
            if (livro.getId() != null) {
                livros.removeIf(l -> l.getId().equals(livro.getId()));
                livros.add(livro);
                redirect.addFlashAttribute("mensagem", "Livro atualizado com sucesso!");
            } else {
                livro.setId(contadorLivros.incrementAndGet());
                livros.add(livro);
                redirect.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
            }
            return "redirect:/";
        }

        // Remover livro
        @GetMapping("/remover/{id}")
        public String remover(@PathVariable Long id, RedirectAttributes redirect) {
            boolean removido = livros.removeIf(l -> l.getId().equals(id));
            if (removido)
                redirect.addFlashAttribute("mensagem", "Livro removido com sucesso!");
            else
                redirect.addFlashAttribute("erro", "Livro não encontrado!");
            return "redirect:/";
        }

        // Apagar conta do usuário logado
        @PostMapping("/apagar-conta")
        public String apagarConta(@RequestParam String senha, RedirectAttributes redirect) {
            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para apagar a conta!");
                return "redirect:/login";
            }

            if (!usuarioLogado.getSenha().equals(senha)) {
                redirect.addFlashAttribute("erro", "Senha incorreta! Conta não foi apagada.");
                return "redirect:/perfil";
            }

            usuarios.removeIf(u -> u.getId().equals(usuarioLogado.getId()));
            usuarioLogado = null;

            redirect.addFlashAttribute("mensagem", "Conta apagada com sucesso!");
            return "redirect:/login";
        }

        // Exibir formulário de cadastro de cartão
        @GetMapping("/cadastrar-cartao")
        public String exibirCadastroCartao(Model model, RedirectAttributes redirect) {
            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para cadastrar um cartão!");
                return "redirect:/login";
            }

            model.addAttribute("cartao", new Cartao());
            return "cadastrarCartao"; // nome do seu HTML
        }

        // Processar cadastro do cartão
        @PostMapping("/salvar-cartao")
        public String salvarCartao(@ModelAttribute Cartao cartao, RedirectAttributes redirect) {
            if (usuarioLogado == null || usuarioLogado.getId() == 0) {
                redirect.addFlashAttribute("erro", "Você precisa estar logado para cadastrar um cartão!");
                return "redirect:/login";
            }

            usuarioLogado.setCartao(cartao);
            redirect.addFlashAttribute("mensagem", "Cartão cadastrado com sucesso!");
            return "redirect:/perfil";
        }


    }
