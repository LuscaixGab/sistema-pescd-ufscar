package br.ufscar.dc.dsw.pescd.config;

import br.ufscar.dc.dsw.pescd.model.Perfil;
import br.ufscar.dc.dsw.pescd.model.Usuario;
import br.ufscar.dc.dsw.pescd.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarUsuarioSeNaoExistir("Administrador do Sistema", "admin@pescd.local", "admin", "admin123",
                Perfil.ADMINISTRADOR);
        criarUsuarioSeNaoExistir("Secretário do Sistema", "secretario@pescd.local", "secretario", "secretario123",
                Perfil.SECRETARIO);
        criarUsuarioSeNaoExistir("Professor Responsável", "professor@pescd.local", "professor", "professor123",
                Perfil.PROFESSOR);
        criarUsuarioSeNaoExistir("Aluno do Sistema", "aluno@pescd.local", "aluno", "aluno123",
                Perfil.ALUNO);
    }

    private void criarUsuarioSeNaoExistir(String nomeCompleto, String email, String nomeUsuario, String senha, Perfil perfil) {
        boolean usuarioExiste = usuarioRepository.findByNomeUsuario(nomeUsuario).isPresent()
                || usuarioRepository.findByEmail(email).isPresent();

        if (usuarioExiste) {
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(nomeCompleto);
        usuario.setEmail(email);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setPerfil(perfil);
        usuarioRepository.save(usuario);
    }
}
