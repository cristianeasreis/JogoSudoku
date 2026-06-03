package com.sudoku.interfaces;

import com.sudoku.model.Dificuldade;
import com.sudoku.model.Tabuleiro;

/**
 * Contrato para geração de puzzles de Sudoku.
 *
 * <p>Princípio OOP aplicado: <b>Abstração</b> — define o contrato de geração
 * de puzzles. <b>Polimorfismo</b> — diferentes implementações podem gerar
 * puzzles com estratégias distintas (aleatório, banco de dados, arquivo, etc.).
 */
public interface IGerador {

    /**
     * Gera um novo tabuleiro de Sudoku com a dificuldade especificada.
     *
     * @param dificuldade o nível de dificuldade desejado
     * @return um novo tabuleiro com células fixas e células vazias para o jogador
     */
    Tabuleiro gerar(Dificuldade dificuldade);
}

