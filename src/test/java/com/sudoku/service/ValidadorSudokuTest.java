package com.sudoku.service;

import com.sudoku.model.Tabuleiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidadorSudoku Tests")
class ValidadorSudokuTest {

    private ValidadorSudoku validador;

    // Tabuleiro de exemplo com solução conhecida
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
    @DisplayName("Movimento válido em linha, coluna e bloco")
    void movimentoValidoDeveRetornarTrue() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        // Posição [0][2] está vazia, valor 4 não conflita
        assertTrue(validador.isMovimentoValido(tabuleiro, 0, 2, 4));
    }

    @Test
    @DisplayName("Movimento inválido por conflito na linha")
    void movimentoInvalidoPorConflitoNaLinha() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        // Linha 0 já tem 5 e 3
        assertFalse(validador.isMovimentoValido(tabuleiro, 0, 2, 5));
    }

    @Test
    @DisplayName("Movimento inválido por conflito na coluna")
    void movimentoInvalidoPorConflitoNaColuna() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        // Coluna 0 já tem 5, 6, 8, 4, 7
        assertFalse(validador.isMovimentoValido(tabuleiro, 2, 0, 6));
    }

    @Test
    @DisplayName("Tabuleiro vazio deve ser considerado válido")
    void tabuleiroVazioDeveSerValido() {
        Tabuleiro tabuleiro = new Tabuleiro();
        assertTrue(validador.isTabuleirValido(tabuleiro));
    }

    @Test
    @DisplayName("Deve resolver um tabuleiro válido")
    void deveResolverTabuleiroValido() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        boolean resolvido = validador.resolver(tabuleiro);
        assertTrue(resolvido);
        assertTrue(validador.isSolucionado(tabuleiro));
    }

    @Test
    @DisplayName("Tabuleiro resolvido deve ser reconhecido como solucionado")
    void tabuleiroResolvidoDeveSerReconhecido() {
        Tabuleiro tabuleiro = new Tabuleiro();
        validador.resolver(tabuleiro);
        assertTrue(validador.isSolucionado(tabuleiro));
    }

    @Test
    @DisplayName("Valor VAZIO sempre é movimento válido")
    void valorVazioSempreValido() {
        Tabuleiro tabuleiro = new Tabuleiro(GRADE_VALIDA);
        assertTrue(validador.isMovimentoValido(tabuleiro, 0, 2, Tabuleiro.VAZIO));
    }
}

