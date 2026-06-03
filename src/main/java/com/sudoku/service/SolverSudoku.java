package com.sudoku.service;

import com.sudoku.interfaces.ISolver;
import com.sudoku.model.Posicao;
import com.sudoku.model.Tabuleiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do algoritmo de resolução de Sudoku por <b>backtracking</b>.
 *
 * <p>Princípio OOP aplicado:
 * <ul>
 *   <li><b>Abstração</b> — implementa {@link ISolver}, separando a estratégia
 *       de resolução da geração e validação.</li>
 *   <li><b>Responsabilidade Única</b> — esta classe tem apenas uma função:
 *       resolver tabuleiros.</li>
 * </ul>
 */
public class SolverSudoku implements ISolver {

    @Override
    public boolean resolver(Tabuleiro tabuleiro) {
        Optional<Posicao> proxima = encontrarProximaVazia(tabuleiro);

        if (proxima.isEmpty()) {
            return tabuleiro.isCompleto();
        }

        Posicao posicao = proxima.get();

        for (int numero : numerosEmbaralhados()) {
            if (podeColocar(tabuleiro, posicao, numero)) {
                tabuleiro.setValor(posicao, numero);

                if (resolver(tabuleiro)) return true;

                tabuleiro.setValor(posicao, Tabuleiro.VAZIO); // backtrack
            }
        }
        return false;
    }

    @Override
    public Optional<Posicao> encontrarProximaVazia(Tabuleiro tabuleiro) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (tabuleiro.isVazio(i, j)) {
                    return Optional.of(new Posicao(i, j));
                }
            }
        }
        return Optional.empty();
    }

    // ── Privados ──────────────────────────────────────────────────────────────

    private boolean podeColocar(Tabuleiro tabuleiro, Posicao posicao, int valor) {
        return !existeNaLinha(tabuleiro, posicao.linha(), valor, posicao.coluna())
            && !existeNaColuna(tabuleiro, posicao.coluna(), valor, posicao.linha())
            && !existeNoBloco(tabuleiro, posicao, valor);
    }

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

    private List<Integer> numerosEmbaralhados() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= Tabuleiro.TAMANHO; i++) numeros.add(i);
        Collections.shuffle(numeros);
        return numeros;
    }
}

