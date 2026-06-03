package com.sudoku.interfaces;

import com.sudoku.model.Posicao;
import com.sudoku.model.Tabuleiro;

import java.util.Optional;

/**
 * Contrato para resolução de tabuleiros Sudoku.
 *
 * <p>Princípio OOP aplicado: <b>Abstração</b> — isola a estratégia de
 * resolução, permitindo trocar o algoritmo (backtracking, constraint
 * propagation, etc.) sem impactar o restante do sistema.
 */
public interface ISolver {

    /**
     * Tenta resolver o tabuleiro preenchendo todas as células vazias.
     *
     * @param tabuleiro o tabuleiro a ser resolvido (modificado in-place)
     * @return {@code true} se o tabuleiro foi resolvido com sucesso
     */
    boolean resolver(Tabuleiro tabuleiro);

    /**
     * Encontra a próxima célula vazia no tabuleiro.
     *
     * @param tabuleiro o tabuleiro a pesquisar
     * @return {@link Optional} com a posição vazia, ou vazio se o tabuleiro estiver completo
     */
    Optional<Posicao> encontrarProximaVazia(Tabuleiro tabuleiro);
}

