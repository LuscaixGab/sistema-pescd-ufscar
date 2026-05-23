package br.ufscar.dc.dsw.pescd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ufscar.dc.dsw.pescd.model.Usuario; // Ajuste para o nome do seu pacote

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Já tem CRUD completo por padrão
}