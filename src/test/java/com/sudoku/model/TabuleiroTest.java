package com.sudoku.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tabuleiro Tests")
class TabuleiroTest {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void setUp() {
        tabuleiro = new Tabuleiro();
    }

    @Test
    @DisplayName("Tabuleiro vazio deve ter todos os valores zerados")
    void tabuleiroVazioDeveSerZerado() {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                assertEquals(Tabuleiro.VAZIO, tabuleiro.getValor(i, j));
            }
        }
    }

    @Test
    @DisplayName("Deve definir e obter valor corretamente")
    void deveDefinirEObterValor() {
        tabuleiro.setValor(0, 0, 5);
        assertEquals(5, tabuleiro.getValor(0, 0));
    }

    @Test
    @DisplayName("Deve lançar exceção para posição inválida")
    void deveLancarExcecaoParaPosicaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> tabuleiro.getValor(9, 0));
        assertThrows(IllegalArgumentException.class, () -> tabuleiro.getValor(0, -1));
    }

    @Test
    @DisplayName("Deve lançar exceção para valor inválido")
    void deveLancarExcecaoParaValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> tabuleiro.setValor(0, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> tabuleiro.setValor(0, 0, -1));
    }

    @Test
    @DisplayName("Célula fixa não deve ser alterada")
    void celulaFixaNaoDeveSerAlterada() {
        int[][] grade = new int[9][9];
        grade[0][0] = 5;
        Tabuleiro tabComFixo = new Tabuleiro(grade);

        boolean resultado = tabComFixo.setValor(0, 0, 3);
        assertFalse(resultado);
        assertEquals(5, tabComFixo.getValor(0, 0));
    }

    @Test
    @DisplayName("Tabuleiro não está completo quando há células vazias")
    void tabuleiroNaoEstaCompleto() {
        assertFalse(tabuleiro.isCompleto());
    }

    @Test
    @DisplayName("toString deve retornar representação visual do tabuleiro")
    void toStringDeveRetornarRepresentacaoVisual() {
        String representacao = tabuleiro.toString();
        assertNotNull(representacao);
        assertTrue(representacao.contains("║"));
    }
}

