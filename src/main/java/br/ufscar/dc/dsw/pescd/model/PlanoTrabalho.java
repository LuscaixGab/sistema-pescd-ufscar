package br.ufscar.dc.dsw.pescd.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "planos_trabalho")
public class PlanoTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String codigoDisciplina;

    @Column(nullable = false)
    private String nomeDisciplina;

    @Column(nullable = false)
    private String cursoDisciplina;

    // Guarda o nome ou o caminho onde o PDF foi salvo no servidor
    @Column(nullable = false)
    private String arquivoPlano;

    // O parecer começa nulo (não tem nullable = false), pois só é preenchido na estória PS.02
    @Column(columnDefinition = "TEXT")
    private String parecer;

    @ManyToOne
    @JoinColumn(name = "professor_supervisor_id", nullable = false)
    private Usuario professorSupervisor;

    @OneToOne
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;
 
    // O Parecer fica de fora do construtor principal pois é avaliado depois.
    public PlanoTrabalho(UUID id, String codigoDisciplina, String nomeDisciplina, String cursoDisciplina, 
                         String arquivoPlano, Usuario professorSupervisor, Inscricao inscricao) {
        this.id = id;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
        this.cursoDisciplina = cursoDisciplina;
        this.arquivoPlano = arquivoPlano;
        this.professorSupervisor = professorSupervisor;
        this.inscricao = inscricao;
    }

    protected PlanoTrabalho() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getCursoDisciplina() {
        return cursoDisciplina;
    }

    public void setCursoDisciplina(String cursoDisciplina) {
        this.cursoDisciplina = cursoDisciplina;
    }

    public String getArquivoPlano() {
        return arquivoPlano;
    }

    public void setArquivoPlano(String arquivoPlano) {
        this.arquivoPlano = arquivoPlano;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public Usuario getProfessorSupervisor() {
        return professorSupervisor;
    }

    public void setProfessorSupervisor(Usuario professorSupervisor) {
        this.professorSupervisor = professorSupervisor;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

}