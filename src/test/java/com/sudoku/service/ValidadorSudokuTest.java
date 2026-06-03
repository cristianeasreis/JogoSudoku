package com.sudoku.service;

import com.sudoku.model.Posicao;
import com.sudoku.model.Tabuleiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorSudoku Tests")
class ValidadorSudokuTest {

    private ValidadorSudoku validador;

    private static final int[][] GRADE_VALIDA = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    @BeforeEach
    void setUp() {
        validador = new ValidadorSudoku();
    }

    @Test
    @DisplayName("Movimento válido via Posicao")
    void movimentoValidoViaPosicao() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertTrue(validador.isMovimentoValido(tabuleiro, new Posicao(0, 2), 4));
    }

    @Test
    @DisplayName("Movimento válido via coordenadas inteiras (sobrecarga)")
    void movimentoValidoViaCoordenadas() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertTrue(validador.isMovimentoValido(tabuleiro, 0, 2, 4));
    }

    @Test
    @DisplayName("Movimento inválido por conflito na linha")
    void movimentoInvalidoPorConflitoNaLinha() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertFalse(validador.isMovimentoValido(tabuleiro, new Posicao(0, 2), 5));
    }

    @Test
    @DisplayName("Movimento inválido por conflito na coluna")
    void movimentoInvalidoPorConflitoNaColuna() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertFalse(validador.isMovimentoValido(tabuleiro, new Posicao(2, 0), 6));
    }

    @Test
    @DisplayName("Tabuleiro vazio deve ser considerado válido")
    void tabuleiroVazioDeveSerValido() {
        assertTrue(validador.isTabuleirValido(new Tabuleiro()));
    }

    @Test
    @DisplayName("isSolucionado retorna false para tabuleiro incompleto")
    void isSolucionadoFalsoParaIncompleto() {
        assertFalse(validador.isSolucionado(new Tabuleiro(GRADE_VALIDA)));
    }

    @Test
    @DisplayName("Valor VAZIO sempre é movimento válido")
    void valorVazioSempreValido() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertTrue(validador.isMovimentoValido(tabuleiro, new Posicao(0, 2), Tabuleiro.VAZIO));
    }
}
