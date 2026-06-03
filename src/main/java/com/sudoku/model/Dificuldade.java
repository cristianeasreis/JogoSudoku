package com.sudoku.model;

/**
 * Representa os níveis de dificuldade do Sudoku.
 */
public enum Dificuldade {
    FACIL(36),      // ~36 células preenchidas
    MEDIO(27),      // ~27 células preenchidas
    DIFICIL(20);    // ~20 células preenchidas

    private final int celulasPreenchidas;

    Dificuldade(int celulasPreenchidas) {
        this.celulasPreenchidas = celulasPreenchidas;
    }

    public int getCelulasPreenchidas() {
        return celulasPreenchidas;
    }

    @Override
    public String toString() {
        return switch (this) {
            case FACIL -> "Fácil";
            case MEDIO -> "Médio";
            case DIFICIL -> "Difícil";
        };
    }
}

