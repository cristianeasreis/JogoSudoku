package com.sudoku.service;

import com.sudoku.model.Tabuleiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável por validar e resolver o tabuleiro Sudoku.
 */
public class ValidadorSudoku {

    /**
     * Verifica se um valor é válido em uma posição específica.
     */
    public boolean isMovimentoValido(Tabuleiro tabuleiro, int linha, int coluna, int valor) {
        if (valor == Tabuleiro.VAZIO) return true;
        return !existeNaLinha(tabuleiro, linha, valor, coluna)
            && !existeNaColuna(tabuleiro, coluna, valor, linha)
            && !existeNoBloco(tabuleiro, linha, coluna, valor);
    }

    /**
     * Verifica se o tabuleiro está em um estado válido (sem conflitos).
     */
    public boolean isTabuleirValido(Tabuleiro tabuleiro) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                int valor = tabuleiro.getValor(i, j);
                if (valor != Tabuleiro.VAZIO) {
                    if (!isMovimentoValidoSemPosicaoAtual(tabuleiro, i, j, valor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Verifica se o tabuleiro foi resolvido corretamente.
     */
    public boolean isSolucionado(Tabuleiro tabuleiro) {
        return tabuleiro.isCompleto() && isTabuleirValido(tabuleiro);
    }

    /**
     * Resolve o tabuleiro usando backtracking.
     * @return true se foi possível resolver
     */
    public boolean resolver(Tabuleiro tabuleiro) {
        int[] posicaoVazia = encontrarPosicaoVazia(tabuleiro);
        if (posicaoVazia == null) {
            return tabuleiro.isCompleto();
        }

        int linha = posicaoVazia[0];
        int coluna = posicaoVazia[1];

        List<Integer> numeros = criarListaEmbaralhada();
        for (int numero : numeros) {
            if (isMovimentoValido(tabuleiro, linha, coluna, numero)) {
                tabuleiro.setValor(linha, coluna, numero);
                if (resolver(tabuleiro)) {
                    return true;
                }
                tabuleiro.setValor(linha, coluna, Tabuleiro.VAZIO);
            }
        }
        return false;
    }

    private boolean existeNaLinha(Tabuleiro tabuleiro, int linha, int valor, int ignorarColuna) {
        for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
            if (j != ignorarColuna && tabuleiro.getValor(linha, j) == valor) {
                return true;
            }
        }
        return false;
    }

    private boolean existeNaColuna(Tabuleiro tabuleiro, int coluna, int valor, int ignorarLinha) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            if (i != ignorarLinha && tabuleiro.getValor(i, coluna) == valor) {
                return true;
            }
        }
        return false;
    }

    private boolean existeNoBloco(Tabuleiro tabuleiro, int linha, int coluna, int valor) {
        int inicioLinha = (linha / Tabuleiro.TAMANHO_BLOCO) * Tabuleiro.TAMANHO_BLOCO;
        int inicioColuna = (coluna / Tabuleiro.TAMANHO_BLOCO) * Tabuleiro.TAMANHO_BLOCO;

        for (int i = inicioLinha; i < inicioLinha + Tabuleiro.TAMANHO_BLOCO; i++) {
            for (int j = inicioColuna; j < inicioColuna + Tabuleiro.TAMANHO_BLOCO; j++) {
                if ((i != linha || j != coluna) && tabuleiro.getValor(i, j) == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMovimentoValidoSemPosicaoAtual(Tabuleiro tabuleiro, int linha, int coluna, int valor) {
        return !existeNaLinha(tabuleiro, linha, valor, coluna)
            && !existeNaColuna(tabuleiro, coluna, valor, linha)
            && !existeNoBloco(tabuleiro, linha, coluna, valor);
    }

    private int[] encontrarPosicaoVazia(Tabuleiro tabuleiro) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (tabuleiro.isVazio(i, j)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private List<Integer> criarListaEmbaralhada() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= Tabuleiro.TAMANHO; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        return numeros;
    }
}

