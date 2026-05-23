package br.ufscar.dc.dsw.pescd.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // estratégia mais segura
    private UUID id;

    @ManyToOne // várias inscrições -> 1 aluno
    @JoinColumn(name = "aluno_id", nullable = false) // coluna extra com id do aluno
    private Usuario aluno;

    @ManyToOne // várias inscrições -> 1 oferta
    @JoinColumn(name = "oferta_id", nullable = false) // coluna extra com id da oferta
    private Oferta oferta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInscricao status;

    public Inscricao(UUID id, Usuario aluno, Oferta oferta, StatusInscricao status) {
        this.id = id;
        this.aluno = aluno;
        this.oferta = oferta;
        this.status = status;
    }

    // Construtor necessário para o Hibernate
    protected Inscricao() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }
}