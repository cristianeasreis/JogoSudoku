# 🧩 Jogo Sudoku — Java 21

> Jogo de Sudoku em modo console desenvolvido em **Java 21** com **Maven**.  
> Gera puzzles aleatórios, valida movimentos em tempo real e suporta três níveis de dificuldade.

---

## 📋 Índice

- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Como Executar](#-como-executar)
- [Como Jogar](#-como-jogar)
- [Dificuldades](#-dificuldades)
- [Comandos do Jogo](#-comandos-do-jogo)
- [Executar Testes](#-executar-testes)
- [Estrutura do Projeto](#️-estrutura-do-projeto)
- [Tecnologias](#️-tecnologias)

---

## ✅ Pré-requisitos

Antes de começar, certifique-se de ter instalado:

| Ferramenta | Versão mínima | Como verificar         |
|------------|---------------|------------------------|
| Java (JDK) | 21            | `java -version`        |
| Maven      | 3.8           | `mvn -version`         |
| Git        | qualquer      | `git --version`        |

### Verificando no terminal

```bash
java -version
# Esperado: openjdk version "21.x.x" ...

mvn -version
# Esperado: Apache Maven 3.x.x ...
```

> 💡 **Não tem Java 21?** Baixe em: https://adoptium.net/

---

## 📥 Instalação

Clone o repositório e acesse a pasta do projeto:

```bash
git clone https://github.com/cristianeasreis/JogoSudoku.git
cd JogoSudoku
```

---

## ▶️ Como Executar

### Opção 1 — Compilar e rodar direto (recomendado para desenvolvimento)

```bash
mvn compile exec:java -Dexec.mainClass="com.sudoku.Main"
```

### Opção 2 — Gerar JAR e executar

```bash
# 1. Gerar o arquivo JAR
mvn package -DskipTests

# 2. Executar o JAR
java -jar target/jogo-sudoku.jar
```

### Opção 3 — Compilar e executar em um só comando

```bash
mvn package -DskipTests && java -jar target/jogo-sudoku.jar
```

---

## 🎮 Como Jogar

Ao iniciar o jogo, você verá o **Menu Principal**:

```
╔══════════════════════════════════╗
║         🧩 JOGO SUDOKU 🧩        ║
║          Java 21 Edition          ║
╚══════════════════════════════════╝

═══════════════════════════
 MENU PRINCIPAL
═══════════════════════════
 1. Novo Jogo
 2. Sair
═══════════════════════════
```

**Passo a passo:**

1. Digite `1` e pressione **Enter** para iniciar um novo jogo
2. Escolha a **dificuldade** (1, 2 ou 3)
3. O tabuleiro aparecerá na tela:

```
╔═══════╦═══════╦═══════╗
║ 5 3 . ║ . 7 . ║ . . . ║
║ 6 . . ║ 1 9 5 ║ . . . ║
║ . 9 8 ║ . . . ║ . 6 . ║
╠═══════╬═══════╬═══════╣
║ 8 . . ║ . 6 . ║ . . 3 ║
║ 4 . . ║ 8 . 3 ║ . . 1 ║
║ 7 . . ║ . 2 . ║ . . 6 ║
╠═══════╬═══════╬═══════╣
║ . 6 . ║ . . . ║ 2 8 . ║
║ . . . ║ 4 1 9 ║ . . 5 ║
║ . . . ║ . 8 . ║ . 7 9 ║
╚═══════╩═══════╩═══════╝
```

4. Para fazer uma jogada, informe: **`linha coluna valor`**

```
> 1 3 4
✅ Movimento válido!
```

> 📍 **Importante:** linhas e colunas vão de **1 a 9** (não de 0 a 8).

---

## 🎯 Dificuldades

| Nível   | Células preenchidas | Células vazias | Indicado para        |
|---------|---------------------|----------------|----------------------|
| Fácil   | ~36                 | ~45            | Iniciantes           |
| Médio   | ~27                 | ~54            | Jogadores casuais    |
| Difícil | ~20                 | ~61            | Jogadores experientes|

---

## ⌨️ Comandos do Jogo

| Comando           | Descrição                                       | Exemplo |
|-------------------|-------------------------------------------------|---------|
| `linha col valor` | Inserir um número no tabuleiro                  | `3 5 7` |
| `d`               | 💡 Revelar uma dica (preenche uma célula vazia)  | `d`     |
| `r`               | 🤖 Resolver o tabuleiro automaticamente          | `r`     |
| `n`               | 🔄 Voltar ao menu e iniciar novo jogo            | `n`     |

### ⚠️ Regras de erro

- Você tem **3 tentativas erradas** por partida
- Um erro ocorre quando o número inserido **conflita** com linha, coluna ou bloco
- Ao atingir 3 erros → **💀 Game Over!**

---

## 🔢 Regras do Sudoku

O objetivo é preencher o tabuleiro 9×9 de forma que:

- ✔️ Cada **linha** contenha os números de 1 a 9 **sem repetição**
- ✔️ Cada **coluna** contenha os números de 1 a 9 **sem repetição**
- ✔️ Cada **bloco 3×3** contenha os números de 1 a 9 **sem repetição**
- ❌ O ponto `.` representa uma célula **vazia**
- 🔒 Números do puzzle inicial são **fixos** e não podem ser alterados

---

## 🧪 Executar Testes

```bash
mvn test
```

Saída esperada:

```
[INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Cobertura de testes

| Classe                | O que é testado                                       |
|-----------------------|-------------------------------------------------------|
| `TabuleiroTest`       | Criação, leitura, escrita, células fixas, validações  |
| `ValidadorSudokuTest` | Movimentos válidos/inválidos, resolução backtracking  |
| `GeradorSudokuTest`   | Geração por dificuldade, solucionabilidade            |

---

## 🏗️ Estrutura do Projeto

```
JogoSudoku/
├── pom.xml                                      ← Configuração Maven (Java 21)
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/sudoku/
    │   ├── Main.java                            ← Ponto de entrada da aplicação
    │   ├── model/
    │   │   ├── Tabuleiro.java                   ← Grade 9x9 com células fixas
    │   │   └── Dificuldade.java                 ← Enum: FACIL / MEDIO / DIFICIL
    │   ├── service/
    │   │   ├── ValidadorSudoku.java             ← Validação + backtracking solver
    │   │   └── GeradorSudoku.java               ← Geração aleatória de puzzles
    │   ├── controller/
    │   │   └── JogoSudoku.java                  ← Lógica do jogo, dicas, erros
    │   └── ui/
    │       └── ConsoleSudoku.java               ← Interface interativa no terminal
    └── test/java/com/sudoku/
        ├── model/
        │   └── TabuleiroTest.java
        └── service/
            ├── ValidadorSudokuTest.java
            └── GeradorSudokuTest.java
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso                                                         |
|------------|--------|-------------------------------------------------------------|
| Java       | 21     | Linguagem principal (records, switch expressions, text blocks) |
| Maven      | 3.8+   | Build e gerenciamento de dependências                       |
| JUnit 5    | 5.10.2 | Testes unitários e parametrizados                           |

---

## 🤝 Contribuindo

1. Faça um **fork** do projeto
2. Crie uma branch: `git checkout -b feature/minha-feature`
3. Commit suas mudanças: `git commit -m 'feat: minha feature'`
4. Push para a branch: `git push origin feature/minha-feature`
5. Abra um **Pull Request**

---

<div align="center">
  Feito com ☕ e Java 21
</div>

