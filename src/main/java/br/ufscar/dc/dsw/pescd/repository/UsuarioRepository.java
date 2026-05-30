package br.ufscar.dc.dsw.pescd.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ufscar.dc.dsw.pescd.model.Usuario; // Ajuste para o nome do seu pacote

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Já tem CRUD completo por padrão
}