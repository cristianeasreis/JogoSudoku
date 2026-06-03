package com.sudoku.controller;

import com.sudoku.model.Dificuldade;
import com.sudoku.model.Tabuleiro;
import com.sudoku.service.GeradorSudoku;
import com.sudoku.service.ValidadorSudoku;

/**
 * Controlador principal do jogo Sudoku.
 * Gerencia o estado do jogo e as interações do jogador.
 */
public class JogoSudoku {

    private Tabuleiro tabuleiro;
    private Tabuleiro solucao;
    private final GeradorSudoku gerador;
    private final ValidadorSudoku validador;
    private boolean jogoAtivo;
    private Dificuldade dificuldadeAtual;
    private int erros;
    private int maxErros;

    public JogoSudoku() {
        this.gerador = new GeradorSudoku();
        this.validador = new ValidadorSudoku();
        this.maxErros = 3;
        this.jogoAtivo = false;
    }

    /**
     * Inicia um novo jogo com a dificuldade especificada.
     */
    public void novoJogo(Dificuldade dificuldade) {
        this.dificuldadeAtual = dificuldade;
        this.tabuleiro = gerador.gerar(dificuldade);
        this.erros = 0;
        this.jogoAtivo = true;

        // Gera a solução
        this.solucao = new Tabuleiro(tabuleiro.getGrade());
        validador.resolver(this.solucao);

        System.out.println("\n🎮 Novo jogo iniciado! Dificuldade: " + dificuldade);
        System.out.println("Máximo de erros permitidos: " + maxErros);
    }

    /**
     * Realiza uma jogada no tabuleiro.
     * @return ResultadoJogada com o resultado da operação
     */
    public ResultadoJogada jogar(int linha, int coluna, int valor) {
        if (!jogoAtivo) {
            return new ResultadoJogada(false, "Nenhum jogo ativo. Inicie um novo jogo.");
        }

        // Validar índices (1-9 para o usuário, 0-8 internamente)
        int l = linha - 1;
        int c = coluna - 1;

        if (l < 0 || l >= Tabuleiro.TAMANHO || c < 0 || c >= Tabuleiro.TAMANHO) {
            return new ResultadoJogada(false, "Posição inválida. Use valores entre 1 e 9.");
        }

        if (tabuleiro.isFixo(l, c)) {
            return new ResultadoJogada(false, "Esta célula é fixa e não pode ser alterada.");
        }

        if (!validador.isMovimentoValido(tabuleiro, l, c, valor)) {
            erros++;
            String msg = "❌ Movimento inválido! Erros: %d/%d".formatted(erros, maxErros);
            if (erros >= maxErros) {
                jogoAtivo = false;
                return new ResultadoJogada(false, msg + "\n💀 Você perdeu! Máximo de erros atingido.");
            }
            return new ResultadoJogada(false, msg);
        }

        tabuleiro.setValor(l, c, valor);

        if (validador.isSolucionado(tabuleiro)) {
            jogoAtivo = false;
            return new ResultadoJogada(true, "🎉 Parabéns! Você completou o Sudoku!");
        }

        return new ResultadoJogada(true, "✅ Movimento válido!");
    }

    /**
     * Exibe uma dica ao jogador (revela um valor correto).
     */
    public String pedirDica() {
        if (!jogoAtivo) return "Nenhum jogo ativo.";

        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (tabuleiro.isVazio(i, j)) {
                    int valorCorreto = solucao.getValor(i, j);
                    tabuleiro.setValor(i, j, valorCorreto);
                    return "💡 Dica: Posição [%d,%d] = %d".formatted(i + 1, j + 1, valorCorreto);
                }
            }
        }
        return "Não há mais células vazias.";
    }

    /**
     * Resolve automaticamente o tabuleiro.
     */
    public void resolverAutomaticamente() {
        if (!jogoAtivo) {
            System.out.println("Nenhum jogo ativo.");
            return;
        }

        // Copia a solução para o tabuleiro
        int[][] gradeSolucao = solucao.getGrade();
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (!tabuleiro.isFixo(i, j)) {
                    tabuleiro.setValor(i, j, gradeSolucao[i][j]);
                }
            }
        }
        jogoAtivo = false;
        System.out.println("🤖 Tabuleiro resolvido automaticamente.");
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean isJogoAtivo() {
        return jogoAtivo;
    }

    public int getErros() {
        return erros;
    }

    public Dificuldade getDificuldadeAtual() {
        return dificuldadeAtual;
    }

    /**
     * Representa o resultado de uma jogada.
     */
    public record ResultadoJogada(boolean sucesso, String mensagem) {}
}

