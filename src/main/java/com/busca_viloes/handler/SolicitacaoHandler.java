package com.busca_viloes.handler;

import com.busca_viloes.model.Solicitacao;
import com.busca_viloes.repository.SolicitacaoRepository;
import com.busca_viloes.util.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class SolicitacaoHandler implements HttpHandler {
    private final SolicitacaoRepository repository;

    public SolicitacaoHandler(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

        try {
            switch (method) {
                case "GET":
                    tratarGet(exchange);
                    break;
                case "POST":
                    tratarPost(exchange);
                    break;
                case "PUT":
                    tratarPutStatus(exchange);
                    break;
                default:
                    enviarResposta(exchange, 405, "{\"mensagem\": \"Metodo nao permitido\"}");
            }
        } catch (Exception e) {
            enviarResposta(exchange, 500, "{\"erro\": \"Erro interno no servidor: " + e.getMessage() + "\"}");
        }
    }

    private void tratarGet(HttpExchange exchange) throws Exception {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            Long id = Long.parseLong(query.split("id=")[1]);
            Optional<Solicitacao> solicitacao = repository.buscarPorId(id);
            if (solicitacao.isPresent()) {
                enviarResposta(exchange, 200, JsonUtils.toJson(solicitacao.get()));
            } else {
                enviarResposta(exchange, 404, "{\"mensagem\": \"Solicitacao nao encontrada\"}");
            }
        } else {
            List<Solicitacao> solicitacoes = repository.buscarTodas();
            enviarResposta(exchange, 200, JsonUtils.toJson(solicitacoes));
        }
    }

    private void tratarPost(HttpExchange exchange) throws Exception {
        Solicitacao novaSolicitacao = JsonUtils.fromJson(exchange.getRequestBody(), Solicitacao.class);
        Solicitacao salva = repository.salvar(novaSolicitacao);
        enviarResposta(exchange, 201, JsonUtils.toJson(salva));
    }

    private void tratarPutStatus(HttpExchange exchange) throws Exception {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=") && query.contains("status=")) {
            String[] params = query.split("&");
            Long id = null;
            String novoStatus = null;

            for (String param : params) {
                if (param.startsWith("id=")) {
                    id = Long.parseLong(param.split("=")[1]);
                } else if (param.startsWith("status=")) {
                    novoStatus = param.split("=")[1];
                }
            }

            if (id != null && novoStatus != null) {
                boolean atualizado = repository.atualizarStatus(id, novoStatus);
                if (atualizado) {
                    enviarResposta(exchange, 200, "{\"mensagem\": \"Status atualizado para " + novoStatus + "\"}");
                } else {
                    enviarResposta(exchange, 404, "{\"mensagem\": \"Solicitacao nao encontrada\"}");
                }
            } else {
                enviarResposta(exchange, 400, "{\"mensagem\": \"Parametros id e status sao obrigatorios\"}");
            }
        } else {
            enviarResposta(exchange, 400, "{\"mensagem\": \"Uso correto: /api/solicitacoes?id=1&status=APROVADA\"}");
        }
    }

    private void enviarResposta(HttpExchange exchange, int statusCode, String respostaJson) throws IOException {
        byte[] bytes = respostaJson.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}