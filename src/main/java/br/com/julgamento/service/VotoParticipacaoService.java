package br.com.julgamento.service;

import br.com.julgamento.domain.enums.ResultadoVotacao;
import br.com.julgamento.web.rest.dto.VotoParticipacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VotoParticipacaoService {

    String realizarVotoSessaoJulgamento(VotoParticipacaoDTO votoParticipacaoDTO);

    ResultadoVotacao apurarVotosSessaoJulgamento(String idSessaoJulgamento);

    Page<VotoParticipacaoDTO> obterVotos(Pageable pageable);
}
