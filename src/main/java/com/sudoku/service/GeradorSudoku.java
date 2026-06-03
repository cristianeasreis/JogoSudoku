package com.sudoku.service;

import com.sudoku.interfaces.IGerador;
import com.sudoku.interfaces.ISolver;
import com.sudoku.model.Dificuldade;
import com.sudoku.model.Tabuleiro;

import java.util.Random;

/**
 * Gerador de puzzles de Sudoku.
 *
 * <p>Princípio OOP aplicado:
 * <ul>
 *   <li><b>Polimorfismo</b> — implementa {@link IGerador}.</li>
 *   <li><b>Inversão de Dependência</b> — recebe {@link ISolver} via
 *       construtor, desacoplando a geração da estratégia de resolução.</li>
 * </ul>
 */
public class GeradorSudoku implements IGerador {

    private final ISolver solver;
    private final Random random;

    public GeradorSudoku(ISolver solver) {
        this.solver = solver;
        this.random = new Random();
    }

    @Override
    public Tabuleiro gerar(Dificuldade dificuldade) {
        // 1. Gera um tabuleiro completamente resolvido
        Tabuleiro tabuleiroCompleto = new Tabuleiro();
        solver.resolver(tabuleiroCompleto);

        // 2. Remove células de acordo com a dificuldade
        int[][] grade = tabuleiroCompleto.getGrade();
        int celulasParaRemover = Tabuleiro.TAMANHO * Tabuleiro.TAMANHO
            - dificuldade.getCelulasPreenchidas();
        removerCelulas(grade, celulasParaRemover);

        return new Tabuleiro(grade);
    }

    // ── Privados ──────────────────────────────────────────────────────────────

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
