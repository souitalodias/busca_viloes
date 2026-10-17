package com.busca_viloes;

import com.busca_viloes.handler.SolicitacaoHandler;
import com.busca_viloes.handler.VilaoHandler;
import com.busca_viloes.repository.SolicitacaoRepository;
import com.busca_viloes.repository.VilaoRepository;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            // Porta onde a API vai escutar (http://localhost:8080)
            int porta = 8080;
            HttpServer server = HttpServer.create(new InetSocketAddress(porta), 0);

            // Instancia os repositórios em memória
            VilaoRepository vilaoRepository = new VilaoRepository();
            SolicitacaoRepository solicitacaoRepository = new SolicitacaoRepository();

            // Mapeia as rotas (endpoints) para seus respectivos Handlers
            server.createContext("/api/viloes", new VilaoHandler(vilaoRepository));
            server.createContext("/api/solicitacoes", new SolicitacaoHandler(solicitacaoRepository));

            // Define o executor como nulo (usa o padrão do sistema)
            server.setExecutor(null);

            // Inicia o servidor HTTP
            server.start();

            System.out.println("=================================================");
            System.out.println(" 🚀 SERVIDOR BUSCA VILÕES RODANDO NA PORTA " + porta);
            System.out.println(" Endpoints disponíveis:");
            System.out.println(" -> http://localhost:8080/api/viloes");
            System.out.println(" -> http://localhost:8080/api/solicitacoes");
            System.out.println("=================================================");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor HTTP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}