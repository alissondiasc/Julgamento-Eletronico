package br.com.julgamento.service.impl;

import br.com.julgamento.domain.SessaoJulgamento;
import br.com.julgamento.domain.Usuario;
import br.com.julgamento.domain.VotoParticipacao;
import br.com.julgamento.domain.enums.ResultadoVotacao;
import br.com.julgamento.repository.VotoParticipacaoRepository;
import br.com.julgamento.service.SessaoJulgamentoService;
import br.com.julgamento.service.VotoParticipacaoService;
import br.com.julgamento.service.mapper.VotoParticipacaoMapper;
import br.com.julgamento.web.rest.dto.VotoParticipacaoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class VotoParticipacaoServiceImpl implements VotoParticipacaoService {

    private VotoParticipacaoRepository votoParticipacaoRepository;
    private VotoParticipacaoMapper votoParticipacaoMapper;
    private SessaoJulgamentoService sessaoJulgamentoService;

    @Override
    public String realizarVotoSessaoJulgamento(VotoParticipacaoDTO votoParticipacaoDTO) {
        try {
            verificarSessaoEncerrada(votoParticipacaoDTO);
            verificarVotoUnico(votoParticipacaoDTO);
            votoParticipacaoRepository.save(votoParticipacaoMapper.dtoParaEntidade(votoParticipacaoDTO));
            return "Operação Relaizada com sucesso.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public ResultadoVotacao apurarVotosSessaoJulgamento(String idSessaoJulgamento) {
        List<VotoParticipacao> votoParticipacaos = votoParticipacaoRepository.findBySessaoJulgamento(SessaoJulgamento.builder().id(idSessaoJulgamento).build());
        if (!votoParticipacaos.isEmpty()) {
            long votosContra = votoParticipacaos.stream().filter(votoParticipacao -> !votoParticipacao.getVoto().getValor()).count();
            long votosAFavor = votoParticipacaos.stream().filter(votoParticipacao -> votoParticipacao.getVoto().getValor()).count();
            ResultadoVotacao resultadoVotacao;
            if (votosContra > votosAFavor) {
                resultadoVotacao = ResultadoVotacao.P;
            } else if (votosAFavor > votosContra) {
                resultadoVotacao = ResultadoVotacao.V;
            } else {
                resultadoVotacao = ResultadoVotacao.E;
            }
            return resultadoVotacao;
        }
        return ResultadoVotacao.E;
    }

    @Override
    public Page<VotoParticipacaoDTO> obterVotos(Pageable pageable) {
        return votoParticipacaoMapper.pageEntidadeParaPageDTO(votoParticipacaoRepository.findAll(pageable));
    }

    public void verificarSessaoEncerrada(VotoParticipacaoDTO votoParticipacaoDTO) throws Exception {
        SessaoJulgamento sessaoJulgamento = sessaoJulgamentoService.obterSessaoJulgamentoPorId(votoParticipacaoDTO.getIdJulgamento());
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        if (sessaoJulgamento.getDataFim().isBefore(dataHoraAtual) || !sessaoJulgamento.getIndSessaoAberta().getValor()) {
            throw new ValidationException("Sessão de julgamento já encerrada.");
        }
    }

    public void verificarVotoUnico(VotoParticipacaoDTO votoParticipacaoDTO) {
        Usuario associado = Usuario.builder().id(votoParticipacaoDTO.getIdAssociado()).build();
        SessaoJulgamento sessaoJulgamento = SessaoJulgamento.builder().id(votoParticipacaoDTO.getIdJulgamento()).build();
        if (votoParticipacaoRepository.findByAssociadoAndSessaoJulgamento(associado, sessaoJulgamento).isPresent()) {
            throw new ValidationException("Não é possivel votar duas vezes para mesma sessão.");
        }
    }


}
