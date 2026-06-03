package com.sudoku;

import com.sudoku.ui.ConsoleSudoku;

/**
 * Ponto de entrada da aplicação Sudoku.
 */
public class Main {

    public static void main(String[] args) {
        var console = new ConsoleSudoku();
        console.iniciar();
    }
}

