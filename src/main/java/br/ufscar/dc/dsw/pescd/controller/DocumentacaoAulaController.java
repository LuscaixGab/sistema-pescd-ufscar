package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.dto.DocumentacaoAulaDTO;
import br.ufscar.dc.dsw.pescd.model.Inscricao;

import br.ufscar.dc.dsw.pescd.model.StatusInscricao; 
import br.ufscar.dc.dsw.pescd.repository.InscricaoRepository;
import br.ufscar.dc.dsw.pescd.service.DocumentacaoAulaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/aluno/documentacao")
public class DocumentacaoAulaController {

    private final DocumentacaoAulaService documentacaoService;
    private final InscricaoRepository inscricaoRepository;

    // Injeção de dependência pelo construtor
    public DocumentacaoAulaController(DocumentacaoAulaService documentacaoService, InscricaoRepository inscricaoRepository) {
        this.documentacaoService = documentacaoService;
        this.inscricaoRepository = inscricaoRepository;
    }

    // GET: Exibe o formulário
    // O aluno vai acessar algo como: /aluno/documentacao/nova/123e4567-e89b-12d3...
    @GetMapping("/nova/{idInscricao}")
    public String exibirFormulario(@PathVariable UUID idInscricao, Model model, RedirectAttributes redirectAttributes) {
        
        // Estar inscrito em uma oferta
        Inscricao inscricao = inscricaoRepository.findById(idInscricao).orElse(null);
        if (inscricao == null) {
            redirectAttributes.addFlashAttribute("erro", "Inscrição não encontrada.");
            return "redirect:/aluno/ofertas"; // TODO: Ajuste para a rota correta da tela inicial do aluno
        }

        // Oferta "em andamento" (checando pelo período das datas)
        java.time.LocalDate hoje = java.time.LocalDate.now();
        java.time.LocalDate inicio = inscricao.getOferta().getDataInicio();
        java.time.LocalDate fim = inscricao.getOferta().getDataFim();

        if (hoje.isBefore(inicio) || hoje.isAfter(fim)) {
            redirectAttributes.addFlashAttribute("erro", "A oferta está fora do período letivo e não está em andamento.");
            return "redirect:/aluno/ofertas";
        }

        // Status do aluno deve ser "não enviado" (ou selecionado para enviar)
        if (inscricao.getStatus() != StatusInscricao.NAO_ENVIADO) {
            redirectAttributes.addFlashAttribute("erro", "Você já enviou a documentação ou não está na fase correta.");
            return "redirect:/aluno/ofertas";
        }

        // Manda o DTO vazio pra tela para o aluno preencher
        model.addAttribute("inscricao", inscricao);
        model.addAttribute("documentacaoDTO", new DocumentacaoAulaDTO());
        return "aluno/formularioDocumentacao";
    }

    // POST: Recebe os dados e o PDF da tela
    @PostMapping("/enviar/{idInscricao}")
    public String processarEnvio(@PathVariable UUID idInscricao, 
                                 @Valid @ModelAttribute("documentacaoDTO") DocumentacaoAulaDTO dto,
                                 BindingResult result, 
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        
        Inscricao inscricao = inscricaoRepository.findById(idInscricao).orElseThrow();

        // Se o Spring achar algum erro nas validações do DTO (@NotBlank, @NotNull)
        if (result.hasErrors()) {
            model.addAttribute("inscricao", inscricao);
            return "aluno/formularioDocumentacao"; // Volta pra tela com os erros
        }

        try {
            // Chama o Service
            documentacaoService.processarEnvio(dto, inscricao);
            redirectAttributes.addFlashAttribute("sucesso", "Documentação enviada com sucesso!");
            return "redirect:/aluno/ofertas"; // Redireciona de volta pra lista

        } catch (IllegalArgumentException e) {
            // Arquivo era maior que 5MB ou não era PDF
            result.rejectValue("arquivo", "error.arquivo", e.getMessage());
            model.addAttribute("inscricao", inscricao);
            return "aluno/formularioDocumentacao";
        } catch (Exception e) {
            // Qualquer outro erro genérico ao salvar o arquivo
            redirectAttributes.addFlashAttribute("erro", "Erro interno ao salvar o arquivo.");
            return "redirect:/aluno/ofertas";
        }
    }
}