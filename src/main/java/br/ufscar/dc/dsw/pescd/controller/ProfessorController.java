package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.model.Inscricao;
import br.ufscar.dc.dsw.pescd.model.StatusInscricao;
import br.ufscar.dc.dsw.pescd.model.Usuario;
import br.ufscar.dc.dsw.pescd.repository.InscricaoRepository;
import br.ufscar.dc.dsw.pescd.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProfessorController(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Listar relatórios pendentes
    @GetMapping("/relatorios/pendentes")
    public String listarRelatoriosPendentes(Authentication authentication, Model model) {
        // Pega o professor que está logado no momento
        Usuario professorLogado = usuarioRepository.findByNomeUsuario(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Professor não logado ou não encontrado"));

        // Busca todas as inscrições que são das ofertas desse professor e que estão aguardando o relatório
        List<Inscricao> pendentes = inscricaoRepository.findByOfertaProfessorResponsavelAndStatus(
                professorLogado, 
                StatusInscricao.RELATORIO_ENVIADO
        );

        model.addAttribute("inscricoes", pendentes);
        return "professor/listarRelatoriosPendentes"; // Renderiza a tabela HTML
    }

    // Tela do formulário de avaliação
    @GetMapping("/relatorios/avaliar/{id}")
    public String abrirTelaAvaliacao(@PathVariable("id") UUID id, Model model) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscrição inválida"));

        model.addAttribute("inscricao", inscricao);
        
        return "professor/avaliarRelatorio"; // Renderiza o formulário do parecer
    }

    // Receber a submissão de aprovação ou reprovação
    @PostMapping("/relatorios/avaliar/{id}")
    public String salvarAvaliacao(@PathVariable("id") UUID id, 
                                  @RequestParam("parecer") String parecer, 
                                  @RequestParam("acao") String acao) {
        
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscrição inválida"));
        
        // Define o novo status baseado no botão que o professor clicou (Aprovar ou Reprovar)
        if ("aprovar".equals(acao)) {
            inscricao.setStatus(StatusInscricao.RELATORIO_APROVADO); // Ou ESTAGIO_FINALIZADO, ajuste conforme seu Enum
        } else if ("reprovar".equals(acao)) {
            inscricao.setStatus(StatusInscricao.RELATORIO_REPROVADO); // Ajuste conforme seu Enum
        }
        
        inscricaoRepository.save(inscricao);

        // Volta para a lista de relatórios pendentes com sucesso
        return "redirect:/professor/relatorios/pendentes?sucesso";
    }
}