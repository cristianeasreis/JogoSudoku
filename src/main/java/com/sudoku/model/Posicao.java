package com.sudoku.model;

/**
 * Value object que representa uma posição (linha, coluna) no tabuleiro.
 *
 * <p>Usa coordenadas internas (0-based). Para entrada do usuário (1-based),
 * utilize o método de fábrica {@link #deEntradaUsuario(int, int)}.
 *
 * <p>Princípio OOP aplicado: <b>Encapsulamento</b> via record imutável.
 */
public record Posicao(int linha, int coluna) {

    /** Validação no construtor compacto do record. */
    public Posicao {
        if (linha < 0 || linha >= Tabuleiro.TAMANHO || coluna < 0 || coluna >= Tabuleiro.TAMANHO) {
            throw new IllegalArgumentException(
                "Posição inválida: [%d][%d]. Deve ser entre 0 e %d."
                    .formatted(linha, coluna, Tabuleiro.TAMANHO - 1)
            );
        }
    }

    /**
     * Cria uma Posição a partir das coordenadas informadas pelo usuário (1-based).
     *
     * @param linha  linha do usuário (1–9)
     * @param coluna coluna do usuário (1–9)
     */
    public static Posicao deEntradaUsuario(int linha, int coluna) {
        return new Posicao(linha - 1, coluna - 1);
    }

    /** Exibe a posição no formato amigável ao usuário (1-based). */
    @Override
    public String toString() {
        return "[linha=%d, coluna=%d]".formatted(linha + 1, coluna + 1);
    }
}

