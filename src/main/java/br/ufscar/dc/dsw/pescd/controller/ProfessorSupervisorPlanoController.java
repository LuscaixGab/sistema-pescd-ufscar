package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.model.PlanoTrabalho;
import br.ufscar.dc.dsw.pescd.security.UsuarioUserDetails;
import br.ufscar.dc.dsw.pescd.service.PlanoTrabalhoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/professor/planos")
@PreAuthorize("hasRole('PROFESSOR')")
public class ProfessorSupervisorPlanoController {

    private final PlanoTrabalhoService planoTrabalhoService;

    public ProfessorSupervisorPlanoController(PlanoTrabalhoService planoTrabalhoService) {
        this.planoTrabalhoService = planoTrabalhoService;
    }

    @GetMapping("/pendentes")
    public String listarPendentes(Model model, @AuthenticationPrincipal UsuarioUserDetails usuarioLogado) {
        model.addAttribute("planos", planoTrabalhoService.listarPlanosPendentes(usuarioLogado.getUsuario()));
        return "professor-supervisor/listarPlanosPendentes";
    }

    @GetMapping("/{id}/avaliar")
    public String exibirFormularioAvaliacao(@PathVariable UUID id, Model model, @AuthenticationPrincipal UsuarioUserDetails usuarioLogado) {
        try {
            model.addAttribute("plano", planoTrabalhoService.buscarPlanoParaAvaliacao(id, usuarioLogado.getUsuario()));
            return "professor-supervisor/avaliarPlano";
        } catch (IllegalArgumentException e) {
            return "redirect:/professor/planos/pendentes?erro=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/avaliar")
    public String processarAvaliacao(@PathVariable UUID id,
                                     @RequestParam("parecer") String parecer,
                                     @RequestParam("acao") String acao,
                                     @AuthenticationPrincipal UsuarioUserDetails usuarioLogado,
                                     RedirectAttributes redirectAttributes) {
        try {
            planoTrabalhoService.avaliarPlano(id, parecer, acao, usuarioLogado.getUsuario());
            redirectAttributes.addFlashAttribute("sucesso", "Plano avaliado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/professor/planos/pendentes";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> baixarPlano(@PathVariable UUID id, @AuthenticationPrincipal UsuarioUserDetails usuarioLogado) {
        try {
            PlanoTrabalho plano = planoTrabalhoService.buscarPlanoParaAvaliacao(id, usuarioLogado.getUsuario());
            Path caminhoArquivo = Paths.get(plano.getArquivoPlano()).normalize();
            Resource resource = new UrlResource(caminhoArquivo.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}