package com.sudoku.model;

/**
 * Representa o tabuleiro do Sudoku (9x9).
 */
public class Tabuleiro {

    public static final int TAMANHO = 9;
    public static final int TAMANHO_BLOCO = 3;
    public static final int VAZIO = 0;

    private final int[][] grade;
    private final boolean[][] fixo; // células originais que não podem ser alteradas

    public Tabuleiro() {
        this.grade = new int[TAMANHO][TAMANHO];
        this.fixo = new boolean[TAMANHO][TAMANHO];
    }

    public Tabuleiro(int[][] gradaInicial) {
        this.grade = new int[TAMANHO][TAMANHO];
        this.fixo = new boolean[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                this.grade[i][j] = gradaInicial[i][j];
                if (gradaInicial[i][j] != VAZIO) {
                    this.fixo[i][j] = true;
                }
            }
        }
    }

    public int getValor(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return grade[linha][coluna];
    }

    public boolean setValor(int linha, int coluna, int valor) {
        validarPosicao(linha, coluna);
        if (fixo[linha][coluna]) {
            return false; // célula fixa, não pode ser alterada
        }
        if (valor < 0 || valor > TAMANHO) {
            throw new IllegalArgumentException("Valor deve ser entre 0 e " + TAMANHO);
        }
        grade[linha][coluna] = valor;
        return true;
    }

    public boolean isFixo(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return fixo[linha][coluna];
    }

    public boolean isVazio(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return grade[linha][coluna] == VAZIO;
    }

    public boolean isCompleto() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] == VAZIO) return false;
            }
        }
        return true;
    }

    public int[][] getGrade() {
        int[][] copia = new int[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            System.arraycopy(grade[i], 0, copia[i], 0, TAMANHO);
        }
        return copia;
    }

    public void limparCelula(int linha, int coluna) {
        validarPosicao(linha, coluna);
        if (!fixo[linha][coluna]) {
            grade[linha][coluna] = VAZIO;
        }
    }

    private void validarPosicao(int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            throw new IllegalArgumentException(
                "Posição inválida: [%d][%d]. Deve ser entre 0 e %d".formatted(linha, coluna, TAMANHO - 1)
            );
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("╔═══════╦═══════╦═══════╗\n");
        for (int i = 0; i < TAMANHO; i++) {
            if (i > 0 && i % TAMANHO_BLOCO == 0) {
                sb.append("╠═══════╬═══════╬═══════╣\n");
            }
            sb.append("║ ");
            for (int j = 0; j < TAMANHO; j++) {
                if (j > 0 && j % TAMANHO_BLOCO == 0) {
                    sb.append("║ ");
                }
                if (grade[i][j] == VAZIO) {
                    sb.append(". ");
                } else {
                    sb.append(grade[i][j]).append(" ");
                }
            }
            sb.append("║\n");
        }
        sb.append("╚═══════╩═══════╩═══════╝");
        return sb.toString();
    }
}

