# 🧩 Jogo Sudoku — Java 21

Jogo de Sudoku em modo console desenvolvido em **Java 21** com **Maven**.

## 🏗️ Estrutura do Projeto

```
JogoSudoku/
├── src/
│   ├── main/java/com/sudoku/
│   │   ├── Main.java                        # Ponto de entrada
│   │   ├── model/
│   │   │   ├── Tabuleiro.java               # Modelo do tabuleiro 9x9
│   │   │   └── Dificuldade.java             # Enum de dificuldades
│   │   ├── service/
│   │   │   ├── ValidadorSudoku.java         # Validação e resolução (backtracking)
│   │   │   └── GeradorSudoku.java           # Geração de puzzles
│   │   ├── controller/
│   │   │   └── JogoSudoku.java              # Controlador principal do jogo
│   │   └── ui/
│   │       └── ConsoleSudoku.java           # Interface de linha de comando
│   └── test/java/com/sudoku/
│       ├── model/TabuleiroTest.java
│       └── service/
│           ├── ValidadorSudokuTest.java
│           └── GeradorSudokuTest.java
└── pom.xml
```

## 🚀 Pré-requisitos

- **Java 21+**
- **Maven 3.8+**

## ▶️ Como Executar

### Compilar e executar

```bash
mvn compile exec:java -Dexec.mainClass="com.sudoku.Main"
```

### Gerar JAR e executar

```bash
mvn package
java -jar target/jogo-sudoku.jar
```

### Executar testes

```bash
mvn test
```

## 🎮 Como Jogar

1. Escolha a **dificuldade**: Fácil, Médio ou Difícil
2. O tabuleiro será exibido com os números iniciais
3. Para jogar, informe: `linha coluna valor` (ex: `3 5 7`)
4. Use `d` para pedir uma **dica**
5. Use `r` para **resolver** automaticamente
6. Use `n` para iniciar um **novo jogo**
7. Você tem **3 erros** antes de perder!

## 🔢 Regras do Sudoku

- Cada linha deve conter os números de **1 a 9** sem repetição
- Cada coluna deve conter os números de **1 a 9** sem repetição
- Cada bloco 3×3 deve conter os números de **1 a 9** sem repetição

## 🛠️ Tecnologias

| Tecnologia | Versão  |
|------------|---------|
| Java       | 21      |
| Maven      | 3.8+    |
| JUnit 5    | 5.10.2  |

