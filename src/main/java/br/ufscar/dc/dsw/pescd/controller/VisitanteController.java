package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.repository.OfertaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitanteController {

    private final OfertaRepository ofertaRepository;

    public VisitanteController(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    @GetMapping("/ofertas-publicas")
    public String listarOfertasVisitante(Model model) {
        // Busca todas as ofertas do banco
        model.addAttribute("ofertas", ofertaRepository.findAll());
        
        // Direciona para o arquivo HTML
        return "/visitante/ofertasPublicas"; 
    }
}