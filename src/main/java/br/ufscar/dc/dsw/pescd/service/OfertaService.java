package br.ufscar.dc.dsw.pescd.service;

import br.ufscar.dc.dsw.pescd.dto.OfertaForm;
import br.ufscar.dc.dsw.pescd.model.Oferta;
import br.ufscar.dc.dsw.pescd.model.Perfil;
import br.ufscar.dc.dsw.pescd.model.Usuario;
import br.ufscar.dc.dsw.pescd.repository.OfertaRepository;
import br.ufscar.dc.dsw.pescd.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final UsuarioRepository usuarioRepository;

    public OfertaService(OfertaRepository ofertaRepository, UsuarioRepository usuarioRepository) {
        this.ofertaRepository = ofertaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepository.findAllByOrderByDataCriacaoDesc();
    }

    public List<Usuario> listarProfessores() {
        return usuarioRepository.findAllByPerfil(Perfil.PROFESSOR);
    }

    public Usuario buscarProfessor(UUID professorResponsavelId) {
        return usuarioRepository.findById(professorResponsavelId)
                .filter(usuario -> usuario.getPerfil() == Perfil.PROFESSOR)
                .orElseThrow(() -> new IllegalArgumentException("Selecione um professor responsável válido."));
    }

    @Transactional
    public Oferta criarOferta(OfertaForm ofertaForm, Usuario usuarioCriador) {
        if (usuarioCriador == null || usuarioCriador.getPerfil() != Perfil.SECRETARIO) {
            throw new IllegalArgumentException("Somente um secretário pode criar uma oferta.");
        }

        Usuario professorResponsavel = buscarProfessor(ofertaForm.getProfessorResponsavelId());

        Oferta oferta = new Oferta();
        oferta.setNomeOferta(StringUtils.hasText(ofertaForm.getNomeOferta())
                ? ofertaForm.getNomeOferta().trim()
                : "Oferta " + ofertaForm.getSemestre().trim() + " - Prof. " + professorResponsavel.getNomeCompleto());
        oferta.setSemestre(ofertaForm.getSemestre().trim());
        oferta.setDataInicio(ofertaForm.getDataInicio());
        oferta.setDataFim(ofertaForm.getDataFim());
        oferta.setProfessorResponsavel(professorResponsavel);
        oferta.setUsuarioCriador(usuarioCriador);

        return ofertaRepository.save(oferta);
    }
}
