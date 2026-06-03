package com.sudoku.controller;

import com.sudoku.interfaces.IGerador;
import com.sudoku.interfaces.ISolver;
import com.sudoku.interfaces.IValidador;
import com.sudoku.model.*;
import com.sudoku.service.GeradorSudoku;
import com.sudoku.service.SolverSudoku;
import com.sudoku.service.ValidadorSudoku;

/**
 * Controlador principal do Jogo Sudoku.
 *
 * <p>Orquestra o ciclo de vida de uma partida: início, jogadas, dicas,
 * resolução automática e encerramento.
 *
 * <p>Princípios OOP aplicados:
 * <ul>
 *   <li><b>Inversão de Dependência</b> — depende das interfaces {@link IGerador},
 *       {@link IValidador} e {@link ISolver}, não de implementações concretas.</li>
 *   <li><b>Encapsulamento</b> — estado do jogo exposto apenas via
 *       {@link EstadoJogo}, sem booleanos espalhados.</li>
 *   <li><b>Factory Method</b> — {@link #criar()} fornece a configuração
 *       padrão sem expor a construção manual ao cliente.</li>
 * </ul>
 */
public class JogoSudoku {

    private static final int MAX_ERROS = 3;

    private final IGerador gerador;
    private final IValidador validador;
    private final ISolver solver;

    private Tabuleiro tabuleiro;
    private Tabuleiro solucao;
    private EstadoJogo estado;
    private Dificuldade dificuldadeAtual;
    private int erros;

    // ── Construtores ──────────────────────────────────────────────────────────

    /**
     * Construtor completo com injeção de dependência.
     * Facilita testes unitários com mocks/stubs.
     */
    public JogoSudoku(IGerador gerador, IValidador validador, ISolver solver) {
        this.gerador = gerador;
        this.validador = validador;
        this.solver = solver;
        this.estado = EstadoJogo.AGUARDANDO;
        this.erros = 0;
    }

    /**
     * Factory method — cria o controlador com as implementações padrão.
     *
     * @return instância pronta para uso
     */
    public static JogoSudoku criar() {
        ISolver solver = new SolverSudoku();
        IValidador validador = new ValidadorSudoku();
        IGerador gerador = new GeradorSudoku(solver);
        return new JogoSudoku(gerador, validador, solver);
    }

    // ── Ciclo de jogo ─────────────────────────────────────────────────────────

    /** Inicia uma nova partida com a dificuldade escolhida. */
    public void novoJogo(Dificuldade dificuldade) {
        this.dificuldadeAtual = dificuldade;
        this.tabuleiro = gerador.gerar(dificuldade);
        this.erros = 0;
        this.estado = EstadoJogo.EM_ANDAMENTO;

        // Pré-computa a solução a partir do estado inicial
        this.solucao = new Tabuleiro(tabuleiro.getGrade());
        solver.resolver(this.solucao);

        System.out.println("\n🎮 Novo jogo iniciado! Dificuldade: " + dificuldade);
        System.out.println("Máximo de erros permitidos: " + MAX_ERROS);
    }

    /**
     * Realiza uma jogada usando as coordenadas do usuário (1-based).
     *
     * @param linha  linha informada pelo usuário (1–9)
     * @param coluna coluna informada pelo usuário (1–9)
     * @param valor  valor a inserir (1–9)
     * @return resultado da jogada
     */
    public ResultadoJogada jogar(int linha, int coluna, int valor) {
        if (!estado.isAtivo()) {
            return ResultadoJogada.falha("Nenhum jogo ativo. Inicie um novo jogo.");
        }

        Posicao posicao;
        try {
            posicao = Posicao.deEntradaUsuario(linha, coluna);
        } catch (IllegalArgumentException e) {
            return ResultadoJogada.falha("Posição inválida. Use valores entre 1 e 9.");
        }

        if (tabuleiro.isFixo(posicao)) {
            return ResultadoJogada.falha("Esta célula é fixa e não pode ser alterada.");
        }

        if (!validador.isMovimentoValido(tabuleiro, posicao, valor)) {
            erros++;
            String msg = "❌ Movimento inválido! Erros: %d/%d".formatted(erros, MAX_ERROS);
            if (erros >= MAX_ERROS) {
                estado = EstadoJogo.PERDEU;
                return ResultadoJogada.falha(msg + "\n💀 " + estado.getDescricao());
            }
            return ResultadoJogada.falha(msg);
        }

        tabuleiro.setValor(posicao, valor);

        if (validador.isSolucionado(tabuleiro)) {
            estado = EstadoJogo.GANHOU;
            return ResultadoJogada.sucesso("🎉 " + estado.getDescricao());
        }

        return ResultadoJogada.sucesso("✅ Movimento válido!");
    }

    /**
     * Revela o valor correto da próxima célula vazia como dica.
     *
     * @return mensagem com a dica ou aviso se não houver células vazias
     */
    public String pedirDica() {
        if (!estado.isAtivo()) return "Nenhum jogo ativo.";

        return solver.encontrarProximaVazia(tabuleiro)
            .map(posicao -> {
                int valorCorreto = solucao.getValor(posicao);
                tabuleiro.setValor(posicao, valorCorreto);
                return "💡 Dica: posição %s = %d".formatted(posicao, valorCorreto);
            })
            .orElse("Não há mais células vazias.");
    }

    /** Resolve o tabuleiro automaticamente usando a solução pré-computada. */
    public void resolverAutomaticamente() {
        if (!estado.isAtivo()) {
            System.out.println("Nenhum jogo ativo.");
            return;
        }

        int[][] gradeSolucao = solucao.getGrade();
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                if (!tabuleiro.isFixo(i, j)) {
                    tabuleiro.setValor(i, j, gradeSolucao[i][j]);
                }
            }
        }
        estado = EstadoJogo.GANHOU;
        System.out.println("🤖 Tabuleiro resolvido automaticamente.");
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Tabuleiro getTabuleiro()         { return tabuleiro; }
    public EstadoJogo getEstado()           { return estado; }
    public boolean isJogoAtivo()           { return estado.isAtivo(); }
    public int getErros()                  { return erros; }
    public int getMaxErros()               { return MAX_ERROS; }
    public Dificuldade getDificuldadeAtual(){ return dificuldadeAtual; }
}
