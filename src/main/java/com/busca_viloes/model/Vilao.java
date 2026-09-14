package com.busca_viloes.model;

public class Vilao {
    private Long id;
    private String nome;          // Ex: Coringa
    private String codinome;     // Ex: Príncipe Palhaço do Crime
    private String nivelAmeaca; // Ex: CRITICO, ALTO, MEDIO
    private double precoDiaria; // Ex: 15000.00
    private boolean disponivel; // true se puder aceitar missões, false se estiver preso ou ocupado

    // Construtor sem argumentos (Necessário para a biblioteca Jackson funcionar)
    public Vilao() {
    }

    // Construtor completo
    public Vilao(Long id, String nome, String codinome, String nivelAmeaca, double precoDiaria, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.codinome = codinome;
        this.nivelAmeaca = nivelAmeaca;
        this.precoDiaria = precoDiaria;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodinome() { return codinome; }
    public void setCodinome(String codinome) { this.codinome = codinome; }

    public String getNivelAmeaca() { return nivelAmeaca; }
    public void setNivelAmeaca(String nivelAmeaca) { this.nivelAmeaca = nivelAmeaca; }

    public double getPrecoDiaria() { return precoDiaria; }
    public void setPrecoDiaria(double precoDiaria) { this.precoDiaria = precoDiaria; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}