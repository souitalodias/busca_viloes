package com.busca_viloes.handler;

import com.busca_viloes.model.Vilao;
import com.busca_viloes.repository.VilaoRepository;
import com.busca_viloes.util.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class VilaoHandler implements HttpHandler {
    private final VilaoRepository repository;

    public VilaoHandler(VilaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        // Configura o cabeçalho para responder sempre em formato JSON e UTF-8
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
                    tratarPut(exchange);
                    break;
                case "DELETE":
                    tratarDelete(exchange);
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

        // Se passar ?id=1 na URL, busca um vilão específico
        if (query != null && query.contains("id=")) {
            Long id = Long.parseLong(query.split("id=")[1]);
            Optional<Vilao> vilao = repository.buscarPorId(id);
            if (vilao.isPresent()) {
                enviarResposta(exchange, 200, JsonUtils.toJson(vilao.get()));
            } else {
                enviarResposta(exchange, 404, "{\"mensagem\": \"Vilao nao encontrado\"}");
            }
        } else {
            // Se não passar ID, traz a lista completa de vilões
            List<Vilao> viloes = repository.buscarTodos();
            enviarResposta(exchange, 200, JsonUtils.toJson(viloes));
        }
    }

    private void tratarPost(HttpExchange exchange) throws Exception {
        Vilao novoVilao = JsonUtils.fromJson(exchange.getRequestBody(), Vilao.class);
        Vilao vilaoSalvo = repository.salvar(novoVilao);
        enviarResposta(exchange, 201, JsonUtils.toJson(vilaoSalvo));
    }

    private void tratarPut(HttpExchange exchange) throws Exception {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            Long id = Long.parseLong(query.split("id=")[1]);
            Vilao vilaoAtualizado = JsonUtils.fromJson(exchange.getRequestBody(), Vilao.class);
            vilaoAtualizado.setId(id);

            boolean sucesso = repository.atualizar(vilaoAtualizado);
            if (sucesso) {
                enviarResposta(exchange, 200, JsonUtils.toJson(vilaoAtualizado));
            } else {
                enviarResposta(exchange, 404, "{\"mensagem\": \"Vilao nao encontrado para atualizacao\"}");
            }
        } else {
            enviarResposta(exchange, 400, "{\"mensagem\": \"Parametro id e obrigatorio na URL\"}");
        }
    }

    private void tratarDelete(HttpExchange exchange) throws Exception {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.contains("id=")) {
            Long id = Long.parseLong(query.split("id=")[1]);
            boolean deletado = repository.deletar(id);
            if (deletado) {
                enviarResposta(exchange, 200, "{\"mensagem\": \"Vilao removido com sucesso\"}");
            } else {
                enviarResposta(exchange, 404, "{\"mensagem\": \"Vilao nao encontrado\"}");
            }
        } else {
            enviarResposta(exchange, 400, "{\"mensagem\": \"Parametro id e obrigatorio na URL\"}");
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