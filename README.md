# 🧩 Jogo Sudoku — Java 21

Jogo de Sudoku em modo console, com foco em arquitetura orientada a objetos, desenvolvido em `Java 21`.

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Como Executar](#-como-executar)
- [Como Jogar](#-como-jogar)
- [Arquitetura OOP](#-arquitetura-oop)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Testes](#-testes)
- [Tecnologias](#-tecnologias)

---

## 🎯 Visão Geral

- Geração de puzzles com 3 dificuldades (`FACIL`, `MEDIO`, `DIFICIL`)
- Validação de jogadas em tempo real
- Controle de estado da partida com `EstadoJogo`
- Dicas e resolução automática
- Separação de responsabilidades usando interfaces e classes especializadas

---

## ✅ Pré-requisitos

| Ferramenta | Versão mínima | Como verificar |
|---|---|---|
| Java (JDK) | 21 | `java -version` |
| Maven | 3.8+ | `mvn -version` |
| Git | qualquer | `git --version` |

> Observação: este projeto foi escrito para Java 21 (`--release 21`).

---

## 📥 Instalação

```bash
git clone https://github.com/cristianeasreis/JogoSudoku.git
cd JogoSudoku
```

---

## ▶️ Como Executar

### 1) Rodar com Maven (fluxo padrão)

```bash
mvn compile exec:java -Dexec.mainClass="com.sudoku.Main"
```

### 2) Empacotar e rodar JAR

```bash
mvn package -DskipTests
java -jar target/jogo-sudoku.jar
```

### 3) Compilar direto com `javac` (alternativa)

```bash
javac --release 21 -d target/classes src/main/java/com/sudoku/Main.java
```

> Se o seu Maven estiver atrás de repositório corporativo e falhar em plugin/dependência, a alternativa com `javac` ajuda a validar os fontes.

---

## 🎮 Como Jogar

1. Abra o jogo e escolha `1` no menu principal
2. Escolha a dificuldade
3. Informe jogadas no formato:

```text
linha coluna valor
```

Exemplo:

```text
3 5 7
```

Comandos disponíveis durante a partida:

| Comando | Ação |
|---|---|
| `linha coluna valor` | Tenta preencher uma célula |
| `d` | Mostra uma dica (preenche próxima célula vazia) |
| `r` | Resolve automaticamente o tabuleiro |
| `n` | Volta ao menu principal |

Regras de erro:

- Máximo de `3` erros por partida
- Ao atingir o limite, o estado vai para `PERDEU`

---

## 🧱 Arquitetura OOP

### Model

- `Celula`: encapsula valor + flag de fixação
- `Posicao` (`record`): representa coordenada válida do tabuleiro
- `EstadoJogo`: estado da partida (`AGUARDANDO`, `EM_ANDAMENTO`, `GANHOU`, `PERDEU`)
- `ResultadoJogada` (`record`): retorno semântico de jogadas
- `Tabuleiro`: agrega `Celula[][]` e expõe API por coordenadas e `Posicao`
- `Dificuldade`: enum com quantidade de células preenchidas

### Interfaces (abstração)

- `IValidador`: contrato de validação
- `ISolver`: contrato para solução de tabuleiro
- `IGerador`: contrato de geração de puzzle

### Services

- `ValidadorSudoku`: aplica regras de linha/coluna/bloco
- `SolverSudoku`: resolve com backtracking
- `GeradorSudoku`: gera puzzle com base na dificuldade

### Controller e UI

- `JogoSudoku`: orquestra fluxo da partida com injeção de dependências
- `ConsoleSudoku`: interface de terminal

Pilares aplicados:

- Encapsulamento
- Abstração
- Polimorfismo (via interfaces)
- Inversão de Dependência
- Responsabilidade Única

---

## 🗂️ Estrutura do Projeto

```text
JogoSudoku/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/sudoku/
    │   ├── Main.java
    │   ├── controller/
    │   │   └── JogoSudoku.java
    │   ├── interfaces/
    │   │   ├── IGerador.java
    │   │   ├── ISolver.java
    │   │   └── IValidador.java
    │   ├── model/
    │   │   ├── Celula.java
    │   │   ├── Dificuldade.java
    │   │   ├── EstadoJogo.java
    │   │   ├── Posicao.java
    │   │   ├── ResultadoJogada.java
    │   │   └── Tabuleiro.java
    │   ├── service/
    │   │   ├── GeradorSudoku.java
    │   │   ├── SolverSudoku.java
    │   │   └── ValidadorSudoku.java
    │   └── ui/
    │       └── ConsoleSudoku.java
    └── test/java/com/sudoku/
        ├── model/
        │   ├── CelulaTest.java
        │   ├── PosicaoTest.java
        │   └── TabuleiroTest.java
        └── service/
            ├── GeradorSudokuTest.java
            └── ValidadorSudokuTest.java
```

---

## 🧪 Testes

Rodar testes com Maven:

```bash
mvn test
```

Cobertura atual principal:

- `CelulaTest`
- `PosicaoTest`
- `TabuleiroTest`
- `ValidadorSudokuTest`
- `GeradorSudokuTest`

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Maven | 3.8+ |
| JUnit | 5.10.2 |

---

<div align="center">
  Feito com ☕ e Java 21
</div>

