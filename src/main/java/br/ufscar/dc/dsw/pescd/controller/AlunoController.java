package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.model.Usuario;
import br.ufscar.dc.dsw.pescd.repository.InscricaoRepository;
import br.ufscar.dc.dsw.pescd.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AlunoController(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/ofertas")
    public String listarOfertas(Model model) {
        // TODO: Remover ID fixo e integrar com o ID da sessão quando a parte de login (U.01) for concluída
        UUID alunoId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        
        // Busca o objeto Usuario com o id detectado
        Usuario alunoLogado = usuarioRepository.findById(alunoId).orElseThrow();

        // Busca as inscrições e ligadas aquele usuário e guarda no model
        model.addAttribute("inscricoes", inscricaoRepository.findByAluno(alunoLogado));

        // Retorna o nome da página no front-end
        return "aluno/ofertas"; 
    }
}