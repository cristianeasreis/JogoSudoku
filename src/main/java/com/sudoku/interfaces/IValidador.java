package com.sudoku.interfaces;

import com.sudoku.model.Posicao;
import com.sudoku.model.Tabuleiro;

/**
 * Contrato de validação de movimentos e estado do tabuleiro Sudoku.
 *
 * <p>Princípio OOP aplicado: <b>Abstração</b> — define o que deve ser feito
 * sem expor como. Permite <b>Polimorfismo</b> — diferentes implementações
 * podem validar com estratégias distintas.
 */
public interface IValidador {

    /**
     * Verifica se um valor pode ser inserido em uma posição sem conflitos.
     *
     * @param tabuleiro o tabuleiro atual
     * @param posicao   a posição alvo
     * @param valor     o valor a testar (0 = sempre válido)
     * @return {@code true} se o movimento não viola nenhuma regra
     */
    boolean isMovimentoValido(Tabuleiro tabuleiro, Posicao posicao, int valor);

    /**
     * Verifica se o estado atual do tabuleiro não contém conflitos.
     *
     * @param tabuleiro o tabuleiro a verificar
     * @return {@code true} se não houver repetições em linhas, colunas ou blocos
     */
    boolean isTabuleirValido(Tabuleiro tabuleiro);

    /**
     * Verifica se o tabuleiro está completamente e corretamente preenchido.
     *
     * @param tabuleiro o tabuleiro a verificar
     * @return {@code true} se estiver completo e sem conflitos
     */
    boolean isSolucionado(Tabuleiro tabuleiro);
}

