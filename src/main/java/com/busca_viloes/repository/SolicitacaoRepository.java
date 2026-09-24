package com.busca_viloes.repository;

import com.busca_viloes.model.Solicitacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class SolicitacaoRepository {
    private final List<Solicitacao> solicitacoes = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public List<Solicitacao> buscarTodas() {
        return new ArrayList<>(solicitacoes);
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return solicitacoes.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Solicitacao salvar(Solicitacao solicitacao) {
        solicitacao.setId(idSequence.getAndIncrement());
        if (solicitacao.getStatus() == null || solicitacao.getStatus().isBlank()) {
            solicitacao.setStatus("PENDENTE");
        }
        solicitacoes.add(solicitacao);
        return solicitacao;
    }

    public boolean atualizarStatus(Long id, String novoStatus) {
        Optional<Solicitacao> existente = buscarPorId(id);
        if (existente.isPresent()) {
            existente.get().setStatus(novoStatus);
            return true;
        }
        return false;
    }
}