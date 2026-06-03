package com.sudoku.service;

import com.sudoku.model.Dificuldade;
import com.sudoku.model.Tabuleiro;

import java.util.Random;

/**
 * Serviço responsável por gerar puzzles de Sudoku.
 */
public class GeradorSudoku {

    private final ValidadorSudoku validador;
    private final Random random;

    public GeradorSudoku() {
        this.validador = new ValidadorSudoku();
        this.random = new Random();
    }

    /**
     * Gera um novo tabuleiro de Sudoku com a dificuldade especificada.
     */
    public Tabuleiro gerar(Dificuldade dificuldade) {
        // Cria tabuleiro completo e válido
        Tabuleiro tabuleiroCompleto = new Tabuleiro();
        validador.resolver(tabuleiroCompleto);

        // Remove células de acordo com a dificuldade
        int[][] gradaCompleta = tabuleiroCompleto.getGrade();
        int celulasParaRemover = Tabuleiro.TAMANHO * Tabuleiro.TAMANHO - dificuldade.getCelulasPreenchidas();

        removerCelulas(gradaCompleta, celulasParaRemover);

        return new Tabuleiro(gradaCompleta);
    }

    private void removerCelulas(int[][] grade, int quantidade) {
        int removidas = 0;
        int tentativas = 0;
        int maxTentativas = quantidade * 10;

        while (removidas < quantidade && tentativas < maxTentativas) {
            int linha = random.nextInt(Tabuleiro.TAMANHO);
            int coluna = random.nextInt(Tabuleiro.TAMANHO);

            if (grade[linha][coluna] != Tabuleiro.VAZIO) {
                grade[linha][coluna] = Tabuleiro.VAZIO;
                removidas++;
            }
            tentativas++;
        }
    }
}

