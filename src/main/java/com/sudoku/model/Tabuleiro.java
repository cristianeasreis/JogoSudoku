package com.sudoku.model;

/**
 * Representa o tabuleiro do Sudoku (9×9).
 *
 * <p>Internamente armazena um array de {@link Celula}, encapsulando o estado
 * de cada posição (valor + fixo). Expõe uma API fluente que aceita tanto
 * coordenadas inteiras quanto o value-object {@link Posicao}.
 *
 * <p>Princípio OOP aplicado: <b>Encapsulamento</b> — o estado interno é
 * protegido e acessado apenas por meio de métodos públicos controlados.
 */
public class Tabuleiro {

    public static final int TAMANHO = 9;
    public static final int TAMANHO_BLOCO = 3;
    public static final int VAZIO = 0;

    private final Celula[][] celulas;

    /** Cria um tabuleiro vazio (sem células fixas). */
    public Tabuleiro() {
        celulas = new Celula[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++)
            for (int j = 0; j < TAMANHO; j++)
                celulas[i][j] = Celula.vazia();
    }

    /** Cria um tabuleiro a partir de uma grade int[][]. Valores != 0 são fixos. */
    public Tabuleiro(int[][] gradeInicial) {
        celulas = new Celula[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                int v = gradeInicial[i][j];
                celulas[i][j] = (v != VAZIO) ? Celula.fixa(v) : Celula.vazia();
            }
        }
    }

    // ── Acesso via coordenadas inteiras ───────────────────────────────────────

    public int getValor(int linha, int coluna) {
        return getCelula(linha, coluna).getValor();
    }

    /**
     * Define o valor de uma célula.
     * @return {@code false} se a célula for fixa; {@code true} se o valor foi aplicado.
     */
    public boolean setValor(int linha, int coluna, int valor) {
        Celula celula = getCelula(linha, coluna);
        if (celula.isFixo()) return false;
        celula.setValor(valor);
        return true;
    }

    public boolean isFixo(int linha, int coluna) {
        return getCelula(linha, coluna).isFixo();
    }

    public boolean isVazio(int linha, int coluna) {
        return getCelula(linha, coluna).isVazio();
    }

    // ── Acesso via Posicao (API OOP) ──────────────────────────────────────────

    public int getValor(Posicao posicao) {
        return getCelula(posicao).getValor();
    }

    public boolean setValor(Posicao posicao, int valor) {
        Celula celula = getCelula(posicao);
        if (celula.isFixo()) return false;
        celula.setValor(valor);
        return true;
    }

    public boolean isFixo(Posicao posicao) {
        return getCelula(posicao).isFixo();
    }

    public boolean isVazio(Posicao posicao) {
        return getCelula(posicao).isVazio();
    }

    public Celula getCelula(Posicao posicao) {
        return getCelula(posicao.linha(), posicao.coluna());
    }

    // ── Estado geral ──────────────────────────────────────────────────────────

    public boolean isCompleto() {
        for (int i = 0; i < TAMANHO; i++)
            for (int j = 0; j < TAMANHO; j++)
                if (celulas[i][j].isVazio()) return false;
        return true;
    }

    /**
     * Retorna uma cópia da grade como int[][] (compatibilidade e serialização).
     */
    public int[][] getGrade() {
        int[][] copia = new int[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++)
            for (int j = 0; j < TAMANHO; j++)
                copia[i][j] = celulas[i][j].getValor();
        return copia;
    }

    public void limparCelula(Posicao posicao) {
        setValor(posicao, VAZIO);
    }

    public void limparCelula(int linha, int coluna) {
        setValor(linha, coluna, VAZIO);
    }

    // ── Interno ───────────────────────────────────────────────────────────────

    private Celula getCelula(int linha, int coluna) {
        validarPosicao(linha, coluna);
        return celulas[linha][coluna];
    }

    private void validarPosicao(int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            throw new IllegalArgumentException(
                "Posição inválida: [%d][%d]. Deve ser entre 0 e %d."
                    .formatted(linha, coluna, TAMANHO - 1)
            );
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("╔═══════╦═══════╦═══════╗\n");
        for (int i = 0; i < TAMANHO; i++) {
            if (i > 0 && i % TAMANHO_BLOCO == 0)
                sb.append("╠═══════╬═══════╬═══════╣\n");
            sb.append("║ ");
            for (int j = 0; j < TAMANHO; j++) {
                if (j > 0 && j % TAMANHO_BLOCO == 0) sb.append("║ ");
                sb.append(celulas[i][j]).append(" ");
            }
            sb.append("║\n");
        }
        sb.append("╚═══════╩═══════╩═══════╝");
        return sb.toString();
    }
}

