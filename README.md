# 📚 Sistema de Cadastro de Livros

Um sistema completo para gerenciamento de acervo literário, desenvolvido com Java Spring Boot e Thymeleaf.

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Descrição

Este é um projeto desenvolvido para colocar em prática conceitos de **Desenvolvimento Web Full-Stack**, utilizando Java no backend e Thymeleaf no frontend. O sistema permite o gerenciamento completo de um acervo literário.

## 🚀 Funcionalidades

O sistema é um **CRUD** completo, permitindo:

- ✅ **Cadastrar:** Adicionar novos livros com título, autor, ano, preço, quantidade e capa
- ✅ **Listar:** Visualizar todos os livros cadastrados em uma interface responsiva
- ✅ **Editar:** Atualizar informações de livros já existentes
- ✅ **Deletar:** Remover livros do acervo de forma definitiva

## 🛠️ Tecnologias

- **Backend**: Java 17, Spring Boot 3.0
- **Frontend**: Thymeleaf, Bootstrap 5, HTML5, CSS3
- **Banco de Dados**: H2 Database (desenvolvimento)
- **Build Tool**: Maven

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- IDE (IntelliJ, VS Code, Eclipse)

## 🔧 Instalação

```bash
# Clone o repositório
git clone https://github.com/KarolineTI/Cadastro-de-Livros.git

# Entre no diretório
cd Cadastro-de-Livros

# Compile o projeto
./mvnw compile

# Execute o projeto
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📂 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/livraria/
│   │   ├── LivrariaApplication.java    # Classe principal
│   │   ├── controller/                 # Controllers
│   │   ├── model/                      # Entidades
│   │   ├── repository/                  # Repositories
│   │   └── service/                    # Serviços
│   └── resources/
│       ├── static/                     # Arquivos estáticos
│       └── templates/                  # Templates Thymeleaf
└── test/                                # Testes
```

## 📱 Screenshots

O sistema conta com interface moderna e intuitiva, permitindo o gerenciamento completo do acervo de livros de forma simples e eficiente.

## 🤝 Contribuição

1. Fork o projeto
2. Crie sua branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Add nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT.

---

⭐ Desenvolvido por **KarolineTI**, **ArthurGdSC** e **Jhenie-ap**
