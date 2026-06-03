package com.sudoku.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Celula Tests")
class CelulaTest {

    @Test
    @DisplayName("Celula.vazia() deve criar célula vazia e não-fixa")
    void celulaVaziaDeveCriarCelulaVazia() {
        Celula celula = Celula.vazia();
        assertEquals(Tabuleiro.VAZIO, celula.getValor());
        assertFalse(celula.isFixo());
        assertTrue(celula.isVazio());
    }

    @Test
    @DisplayName("Celula.fixa() deve criar célula com valor fixo")
    void celulaFixaDeveCriarCelulaFixa() {
        Celula celula = Celula.fixa(7);
        assertEquals(7, celula.getValor());
        assertTrue(celula.isFixo());
        assertFalse(celula.isVazio());
    }

    @Test
    @DisplayName("setValor em célula não-fixa deve funcionar")
    void setValorEmCelulaNaoFixaDeveFuncionar() {
        Celula celula = Celula.vazia();
        celula.setValor(5);
        assertEquals(5, celula.getValor());
    }

    @Test
    @DisplayName("setValor em célula fixa deve lançar exceção")
    void setValorEmCelulaFixaDeveLancarExcecao() {
        Celula celula = Celula.fixa(3);
        assertThrows(IllegalStateException.class, () -> celula.setValor(8));
        assertEquals(3, celula.getValor()); // valor não alterado
    }

    @Test
    @DisplayName("setValor com valor fora do intervalo deve lançar exceção")
    void setValorForaDoIntervaloPorLancarExcecao() {
        Celula celula = Celula.vazia();
        assertThrows(IllegalArgumentException.class, () -> celula.setValor(10));
        assertThrows(IllegalArgumentException.class, () -> celula.setValor(-1));
    }

    @Test
    @DisplayName("toString deve retornar ponto para célula vazia")
    void toStringCelulaVaziaDeveRetornarPonto() {
        assertEquals(".", Celula.vazia().toString());
    }

    @Test
    @DisplayName("toString deve retornar o valor para célula preenchida")
    void toStringCelulaPreenchidaDeveRetornarValor() {
        assertEquals("5", Celula.fixa(5).toString());
    }
}

