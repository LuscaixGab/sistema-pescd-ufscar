package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.model.Usuario;
import br.ufscar.dc.dsw.pescd.repository.InscricaoRepository;
import br.ufscar.dc.dsw.pescd.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.ufscar.dc.dsw.pescd.model.Perfil;
import br.ufscar.dc.dsw.pescd.dto.AlunoCadastroDTO;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AlunoController(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/ofertas")
    public String listarOfertas(Model model, Principal principal) {
        
        // Contém o que foi digitado no login (pode ser "aluno" ou "aluno@pescd.local")
        String identificador = principal.getName();

        // Busca por nome_usuario OR email usando a mesma string
        Usuario alunoLogado = usuarioRepository.findByNomeUsuarioOrEmail(identificador, identificador)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado: " + identificador));

        // Busca as inscrições ligadas àquele usuário e guarda no model
        model.addAttribute("inscricoes", inscricaoRepository.findByAluno(alunoLogado));

        return "aluno/ofertas";
    }
    
    // Rota GET: Entrega o DTO vazio para a tela desenhar o formulário
    @GetMapping("/novo")
    public String exibirFormularioNovoAluno(Model model) {
        model.addAttribute("alunoDTO", new AlunoCadastroDTO());
        return "aluno/adicionarAluno";
    }

    // Rota POST: Recebe o DTO preenchido quando o secretário clica em "Salvar"
    @PostMapping("/novo")
    public String salvarNovoAluno(@Valid @ModelAttribute("alunoDTO") AlunoCadastroDTO alunoDTO,
                                  BindingResult result,
                                  Model model) {
        
        // Se o secretário esqueceu algum campo, devolve para a tela mostrando os erros
        if (result.hasErrors()) {
            return "aluno/adicionarAluno";
        }

        // Verifica se já existe um aluno com esse e-mail ou RA no banco
        if (usuarioRepository.findByEmail(alunoDTO.getEmail()).isPresent() || 
            usuarioRepository.findByNomeUsuario(alunoDTO.getNomeUsuario()).isPresent()) {
            model.addAttribute("erro", "Já existe um usuário cadastrado com este E-mail ou RA.");
            return "aluno/adicionarAluno";
        }

        // DTO validado com sucesso! Agora convertemos para a Entidade real:
        Usuario novoAluno = new Usuario();
        novoAluno.setNomeCompleto(alunoDTO.getNomeCompleto());
        novoAluno.setEmail(alunoDTO.getEmail());
        novoAluno.setNomeUsuario(alunoDTO.getNomeUsuario());
        
        // Criptografa a senha antes de jogar no banco
        novoAluno.setSenha(passwordEncoder.encode(alunoDTO.getSenha()));
        
        // Trava o perfil exclusivamente como ALUNO, garantindo a segurança
        novoAluno.setPerfil(Perfil.ALUNO);

        usuarioRepository.save(novoAluno);

        // Volta para a tela anterior (ou painel) após o sucesso
        return "redirect:/painel"; 
    }
}