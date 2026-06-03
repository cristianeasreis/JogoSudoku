package com.sudoku.service;

import com.sudoku.model.Dificuldade;
import com.sudoku.model.Tabuleiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GeradorSudoku Tests")
class GeradorSudokuTest {

    private GeradorSudoku gerador;
    private ValidadorSudoku validador;

    @BeforeEach
    void setUp() {
        gerador = new GeradorSudoku();
        validador = new ValidadorSudoku();
    }

    @ParameterizedTest
    @EnumSource(Dificuldade.class)
    @DisplayName("Tabuleiro gerado deve ser válido para todas as dificuldades")
    void tabuleiroGeradoDeveSerValido(Dificuldade dificuldade) {
        Tabuleiro tabuleiro = gerador.gerar(dificuldade);

        assertNotNull(tabuleiro);
        assertTrue(validador.isTabuleirValido(tabuleiro));
    }

    @ParameterizedTest
    @EnumSource(Dificuldade.class)
    @DisplayName("Tabuleiro gerado deve ter células preenchidas e vazias")
    void tabuleiroGeradoDeveTerCelulasPreenchidas(Dificuldade dificuldade) {
        Tabuleiro tabuleiro = gerador.gerar(dificuldade);
        int[][] grade = tabuleiro.getGrade();

        int preenchidas = 0;
        int vazias = 0;

        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (grade[i][j] == Tabuleiro.VAZIO) vazias++;
                else preenchidas++;
            }
        }

        assertTrue(preenchidas > 0, "Deve ter células preenchidas");
        assertTrue(vazias > 0, "Deve ter células vazias para o jogador completar");
    }

    @ParameterizedTest
    @EnumSource(Dificuldade.class)
    @DisplayName("Tabuleiro gerado deve ser solucionável")
    void tabuleiroGeradoDeveSerSolucionavel(Dificuldade dificuldade) {
        Tabuleiro tabuleiro = gerador.gerar(dificuldade);
        Tabuleiro copia = new Tabuleiro(tabuleiro.getGrade());

        boolean solucionavel = validador.resolver(copia);
        assertTrue(solucionavel, "O tabuleiro gerado deve ter solução");
    }
}

