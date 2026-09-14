package com.busca_viloes.model;

public class Solicitacao {
    private Long id;
    private Long vilaoId;         // ID do vilão que o cliente quer contratar
    private String nomeCliente;   // Ex: Dr. Destino
    private String emailCliente;  // Ex: destino@caos.com
    private String detalhesPlano; // Ex: Destruir o laboratório do rival
    private String status;        // PENDENTE, APROVADA, RECUSADA

    // Construtor sem argumentos
    public Solicitacao() {
    }

    // Construtor completo
    public Solicitacao(Long id, Long vilaoId, String nomeCliente, String emailCliente, String detalhesPlano, String status) {
        this.id = id;
        this.vilaoId = vilaoId;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.detalhesPlano = detalhesPlano;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVilaoId() { return vilaoId; }
    public void setVilaoId(Long vilaoId) { this.vilaoId = vilaoId; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getDetalhesPlano() { return detalhesPlano; }
    public void setDetalhesPlano(String detalhesPlano) { this.detalhesPlano = detalhesPlano; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}