package br.ufscar.dc.dsw.pescd.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ufscar.dc.dsw.pescd.model.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, UUID> {
    // Já tem CRUD completo por padrão
}