package br.ufscar.dc.dsw.pescd.controller;

import br.ufscar.dc.dsw.pescd.security.UsuarioUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/painel")
    public String exibirPainel(@AuthenticationPrincipal UsuarioUserDetails usuarioLogado, Model model) {
        model.addAttribute("usuario", usuarioLogado.getUsuario());
        return "painel";
    }
}
