package com.rafael2011.show.game;

import java.util.Map;

public class Question {
    private final String pergunta;
    private final Map<String, String> opcoes;
    private final String resposta;

    public Question(String pergunta, Map<String, String> opcoes, String resposta) {
        this.pergunta = pergunta;
        this.opcoes = opcoes;
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public Map<String, String> getOpcoes() {
        return opcoes;
    }

    public String getResposta() {
        return resposta;
    }
}
