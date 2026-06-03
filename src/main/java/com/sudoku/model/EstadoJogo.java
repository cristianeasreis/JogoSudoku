package com.sudoku.model;

/**
 * Representa os possíveis estados de uma partida de Sudoku.
 *
 * <p>Princípio OOP aplicado: <b>Abstração</b> — o estado do jogo é expresso
 * como um tipo rico com comportamento próprio, em vez de flags booleanos.
 */
public enum EstadoJogo {

    AGUARDANDO("Aguardando início"),
    EM_ANDAMENTO("Em andamento"),
    GANHOU("Vitória! 🎉"),
    PERDEU("Game Over 💀");

    private final String descricao;

    EstadoJogo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /** @return true se o jogo ainda aceita jogadas. */
    public boolean isAtivo() {
        return this == EM_ANDAMENTO;
    }

    /** @return true se a partida já foi encerrada (ganhou ou perdeu). */
    public boolean isEncerrado() {
        return this == GANHOU || this == PERDEU;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

