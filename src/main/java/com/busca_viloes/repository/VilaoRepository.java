package com.busca_viloes.repository;

import com.busca_viloes.model.Vilao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class VilaoRepository {
    private final List<Vilao> viloes = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public VilaoRepository() {
        // Carga inicial de testes para a API já subir com dados
        salvar(new Vilao(null, "Coringa", "Príncipe Palhaço", "CRITICO", 15000.0, true));
        salvar(new Vilao(null, "Loki", "Deus da Trapaça", "ALTO", 25000.0, true));
        salvar(new Vilao(null, "Bowser", "Rei dos Koopas", "MEDIO", 8000.0, false));
    }

    public List<Vilao> buscarTodos() {
        return new ArrayList<>(viloes);
    }

    public Optional<Vilao> buscarPorId(Long id) {
        return viloes.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
    }

    public Vilao salvar(Vilao vilao) {
        if (vilao.getId() == null) {
            vilao.setId(idSequence.getAndIncrement());
            viloes.add(vilao);
        } else {
            atualizar(vilao);
        }
        return vilao;
    }

    public boolean atualizar(Vilao vilaoAtualizado) {
        Optional<Vilao> existente = buscarPorId(vilaoAtualizado.getId());
        if (existente.isPresent()) {
            Vilao v = existente.get();
            v.setNome(vilaoAtualizado.getNome());
            v.setCodinome(vilaoAtualizado.getCodinome());
            v.setNivelAmeaca(vilaoAtualizado.getNivelAmeaca());
            v.setPrecoDiaria(vilaoAtualizado.getPrecoDiaria());
            v.setDisponivel(vilaoAtualizado.isDisponivel());
            return true;
        }
        return false;
    }

    public boolean deletar(Long id) {
        return viloes.removeIf(v -> v.getId().equals(id));
    }
}