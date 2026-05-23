package br.ufscar.dc.dsw.pescd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ufscar.dc.dsw.pescd.model.DocumentacaoAula;
import java.util.UUID;

@Repository
public interface DocumentacaoAulaRepository extends JpaRepository<DocumentacaoAula, UUID> {
}