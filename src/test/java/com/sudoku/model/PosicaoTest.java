package com.sudoku.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Posicao Tests")
class PosicaoTest {

    @Test
    @DisplayName("Deve criar Posicao válida com coordenadas 0-based")
    void deveCriarPosicaoValida() {
        Posicao posicao = new Posicao(0, 0);
        assertEquals(0, posicao.linha());
        assertEquals(0, posicao.coluna());
    }

    @Test
    @DisplayName("Deve lançar exceção para posição fora do intervalo")
    void deveLancarExcecaoParaPosicaoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Posicao(9, 0));
        assertThrows(IllegalArgumentException.class, () -> new Posicao(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Posicao(-1, 0));
    }

    @Test
    @DisplayName("deEntradaUsuario deve converter de 1-based para 0-based")
    void deEntradaUsuarioDeveConverterCoordenadas() {
        Posicao posicao = Posicao.deEntradaUsuario(1, 1);
        assertEquals(0, posicao.linha());
        assertEquals(0, posicao.coluna());

        Posicao ultima = Posicao.deEntradaUsuario(9, 9);
        assertEquals(8, ultima.linha());
        assertEquals(8, ultima.coluna());
    }

    @Test
    @DisplayName("deEntradaUsuario deve lançar exceção para valores inválidos")
    void deEntradaUsuarioDeveValidar() {
        assertThrows(IllegalArgumentException.class, () -> Posicao.deEntradaUsuario(0, 1));
        assertThrows(IllegalArgumentException.class, () -> Posicao.deEntradaUsuario(10, 1));
    }

    @Test
    @DisplayName("Duas posições iguais devem ser iguais (record equality)")
    void duasPosicoesDeveremSerIguais() {
        Posicao p1 = new Posicao(3, 4);
        Posicao p2 = new Posicao(3, 4);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("toString deve exibir coordenadas 1-based")
    void toStringDeveExibirCoordenadas1Based() {
        Posicao posicao = new Posicao(0, 0);
        assertTrue(posicao.toString().contains("1"));
    }
}

