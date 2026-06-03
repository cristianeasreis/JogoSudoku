package com.sudoku.service;

import com.sudoku.interfaces.IValidador;
import com.sudoku.model.Posicao;
import com.sudoku.model.Tabuleiro;

/**
 * Validador de movimentos e estado do tabuleiro Sudoku.
 *
 * <p>Princípio OOP aplicado:
 * <ul>
 *   <li><b>Polimorfismo</b> — implementa {@link IValidador}, podendo ser
 *       substituída por outra estratégia de validação sem alterar os clientes.</li>
 *   <li><b>Responsabilidade Única</b> — cuida apenas da validação das regras
 *       do Sudoku. A resolução foi delegada para {@link SolverSudoku}.</li>
 * </ul>
 */
public class ValidadorSudoku implements IValidador {

    @Override
    public boolean isMovimentoValido(Tabuleiro tabuleiro, Posicao posicao, int valor) {
        if (valor == Tabuleiro.VAZIO) return true;
        return !existeNaLinha(tabuleiro, posicao.linha(), valor, posicao.coluna())
            && !existeNaColuna(tabuleiro, posicao.coluna(), valor, posicao.linha())
            && !existeNoBloco(tabuleiro, posicao, valor);
    }

    /** Sobrecarga para compatibilidade com coordenadas inteiras. */
    public boolean isMovimentoValido(Tabuleiro tabuleiro, int linha, int coluna, int valor) {
        return isMovimentoValido(tabuleiro, new Posicao(linha, coluna), valor);
    }

    @Override
    public boolean isTabuleirValido(Tabuleiro tabuleiro) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                int valor = tabuleiro.getValor(i, j);
                if (valor != Tabuleiro.VAZIO) {
                    if (!isMovimentoValido(tabuleiro, new Posicao(i, j), valor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isSolucionado(Tabuleiro tabuleiro) {
        return tabuleiro.isCompleto() && isTabuleirValido(tabuleiro);
    }

    // ── Privados ──────────────────────────────────────────────────────────────

    private boolean existeNaLinha(Tabuleiro t, int linha, int valor, int ignorarColuna) {
        for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
            if (j != ignorarColuna && t.getValor(linha, j) == valor) return true;
        }
        return false;
    }

    private boolean existeNaColuna(Tabuleiro t, int coluna, int valor, int ignorarLinha) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            if (i != ignorarLinha && t.getValor(i, coluna) == valor) return true;
        }
        return false;
    }

    private boolean existeNoBloco(Tabuleiro t, Posicao posicao, int valor) {
        int inicioLinha = (posicao.linha() / Tabuleiro.TAMANHO_BLOCO) * Tabuleiro.TAMANHO_BLOCO;
        int inicioColuna = (posicao.coluna() / Tabuleiro.TAMANHO_BLOCO) * Tabuleiro.TAMANHO_BLOCO;

        for (int i = inicioLinha; i < inicioLinha + Tabuleiro.TAMANHO_BLOCO; i++) {
            for (int j = inicioColuna; j < inicioColuna + Tabuleiro.TAMANHO_BLOCO; j++) {
                if ((i != posicao.linha() || j != posicao.coluna()) && t.getValor(i, j) == valor) {
                    return true;
                }
            }
        }
        return false;
    }
}
