package br.ufscar.dc.dsw.pescd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nomeOferta;

    @Column(nullable = false)
    private String semestre;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "professor_responsavel_id", nullable = false)
    private Usuario professorResponsavel;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "usuario_criador_id", nullable = false, updatable = false)
    private Usuario usuarioCriador;

    public Oferta(UUID id, String nomeOferta, String semestre, LocalDate dataInicio, LocalDate dataFim,
            Usuario professorResponsavel) {
        this.id = id;
        this.nomeOferta = nomeOferta;
        this.semestre = semestre;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.professorResponsavel = professorResponsavel;
    }

    public Oferta() {
    }

    @PrePersist
    protected void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeOferta() {
        return nomeOferta;
    }

    public void setNomeOferta(String nomeOferta) {
        this.nomeOferta = nomeOferta;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Usuario getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(Usuario professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(Usuario usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }
}
