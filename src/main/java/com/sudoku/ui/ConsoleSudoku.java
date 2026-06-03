package com.sudoku.ui;

import com.sudoku.controller.JogoSudoku;
import com.sudoku.model.Dificuldade;

import java.util.Scanner;

/**
 * Interface de linha de comando do Jogo Sudoku.
 *
 * <p>Princípio OOP aplicado: <b>Separação de Responsabilidades</b> —
 * esta classe cuida apenas da interação com o usuário via console,
 * delegando toda a lógica ao {@link JogoSudoku}.
 */
public class ConsoleSudoku {

    private final JogoSudoku jogo;
    private final Scanner scanner;

    public ConsoleSudoku() {
        this.jogo = JogoSudoku.criar();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        exibirBoasVindas();

        boolean continuar = true;
        while (continuar) {
            exibirMenuPrincipal();
            String opcao = scanner.nextLine().trim();

            continuar = switch (opcao) {
                case "1" -> { iniciarNovoJogo(); yield true; }
                case "2" -> { System.out.println("\n👋 Obrigado por jogar! Até logo!"); yield false; }
                default  -> { System.out.println("❗ Opção inválida. Tente novamente."); yield true; }
            };
        }
        scanner.close();
    }

    // ── Menu principal ────────────────────────────────────────────────────────

    private void exibirBoasVindas() {
        System.out.println("""
            ╔══════════════════════════════════╗
            ║         🧩 JOGO SUDOKU 🧩        ║
            ║          Java 21 Edition          ║
            ╚══════════════════════════════════╝
            """);
    }

    private void exibirMenuPrincipal() {
        System.out.println("""
            ═══════════════════════════
             MENU PRINCIPAL
            ═══════════════════════════
             1. Novo Jogo
             2. Sair
            ═══════════════════════════""");
        System.out.print("> ");
    }

    // ── Novo jogo ─────────────────────────────────────────────────────────────

    private void iniciarNovoJogo() {
        Dificuldade dificuldade = escolherDificuldade();
        if (dificuldade == null) return;

        jogo.novoJogo(dificuldade);
        loopDeJogo();
    }

    private Dificuldade escolherDificuldade() {
        System.out.println("""
            
            Escolha a dificuldade:
            ─────────────────────
             1. Fácil   (~36 números)
             2. Médio   (~27 números)
             3. Difícil (~20 números)
             0. Voltar
            """);
        System.out.print("> ");

        return switch (scanner.nextLine().trim()) {
            case "1" -> Dificuldade.FACIL;
            case "2" -> Dificuldade.MEDIO;
            case "3" -> Dificuldade.DIFICIL;
            case "0" -> null;
            default  -> { System.out.println("❗ Opção inválida, usando Fácil."); yield Dificuldade.FACIL; }
        };
    }

    // ── Loop de jogo ──────────────────────────────────────────────────────────

    private void loopDeJogo() {
        while (jogo.isJogoAtivo()) {
            System.out.println("\n" + jogo.getTabuleiro());
            System.out.printf("Estado: %s | Erros: %d/%d%n",
                jogo.getEstado(), jogo.getErros(), jogo.getMaxErros());
            exibirMenuJogo();

            String entrada = scanner.nextLine().trim().toLowerCase();

            switch (entrada) {
                case "d" -> System.out.println(jogo.pedirDica());
                case "r" -> {
                    jogo.resolverAutomaticamente();
                    System.out.println(jogo.getTabuleiro());
                }
                case "n" -> { System.out.println("Voltando ao menu principal..."); return; }
                default  -> processarJogada(entrada);
            }
        }

        if (jogo.getTabuleiro() != null) {
            System.out.println("\n" + jogo.getTabuleiro());
            System.out.println("Estado final: " + jogo.getEstado());
        }
    }

    private void exibirMenuJogo() {
        System.out.println("""
            ─────────────────────────────────────
            Comandos:
              linha coluna valor  → Ex: 3 5 7
              d                  → 💡 Dica
              r                  → 🤖 Resolver
              n                  → 🔄 Novo jogo
            ─────────────────────────────────────""");
        System.out.print("> ");
    }

    private void processarJogada(String entrada) {
        try {
            String[] partes = entrada.trim().split("\\s+");
            if (partes.length != 3) {
                System.out.println("❗ Formato inválido. Use: linha coluna valor (ex: 3 5 7)");
                return;
            }
            int linha  = Integer.parseInt(partes[0]);
            int coluna = Integer.parseInt(partes[1]);
            int valor  = Integer.parseInt(partes[2]);

            var resultado = jogo.jogar(linha, coluna, valor);
            System.out.println(resultado.mensagem());

        } catch (NumberFormatException e) {
            System.out.println("❗ Valores inválidos. Use apenas números inteiros.");
        }
    }
}
