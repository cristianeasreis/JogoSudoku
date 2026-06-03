package com.sudoku.model;

/**
 * Representa uma única célula do tabuleiro Sudoku.
 *
 * <p>Encapsula o valor e o estado de fixação da célula,
 * garantindo que células fixas não possam ser alteradas externamente.
 *
 * <p>Princípio OOP aplicado: <b>Encapsulamento</b>
 */
public class Celula {

    private int valor;
    private final boolean fixo;

    public Celula(int valor, boolean fixo) {
        this.valor = valor;
        this.fixo = fixo;
    }

    /** Cria uma célula vazia e não-fixa. */
    public static Celula vazia() {
        return new Celula(Tabuleiro.VAZIO, false);
    }

    /** Cria uma célula com valor fixo (não editável pelo jogador). */
    public static Celula fixa(int valor) {
        return new Celula(valor, true);
    }

    public int getValor() {
        return valor;
    }

    /**
     * Define o valor da célula.
     *
     * @throws IllegalStateException se a célula for fixa
     * @throws IllegalArgumentException se o valor estiver fora do intervalo permitido
     */
    public void setValor(int valor) {
        if (fixo) {
            throw new IllegalStateException("Célula fixa não pode ser alterada.");
        }
        if (valor < 0 || valor > Tabuleiro.TAMANHO) {
            throw new IllegalArgumentException(
                "Valor deve ser entre 0 e %d.".formatted(Tabuleiro.TAMANHO)
            );
        }
        this.valor = valor;
    }

    public boolean isFixo() {
        return fixo;
    }

    public boolean isVazio() {
        return valor == Tabuleiro.VAZIO;
    }

    @Override
    public String toString() {
        return isVazio() ? "." : String.valueOf(valor);
    }
}

