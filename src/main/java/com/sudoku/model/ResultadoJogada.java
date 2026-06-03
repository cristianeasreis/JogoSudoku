package com.sudoku.model;

/**
 * Value object que representa o resultado de uma jogada.
 *
 * <p>Fornece métodos de fábrica estáticos para maior legibilidade no código.
 *
 * <p>Princípio OOP aplicado: <b>Encapsulamento</b> via record imutável.
 */
public record ResultadoJogada(boolean sucesso, String mensagem) {

    /** Cria um resultado de jogada bem-sucedida. */
    public static ResultadoJogada sucesso(String mensagem) {
        return new ResultadoJogada(true, mensagem);
    }

    /** Cria um resultado de jogada com falha. */
    public static ResultadoJogada falha(String mensagem) {
        return new ResultadoJogada(false, mensagem);
    }
}

