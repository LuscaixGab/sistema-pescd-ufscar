package br.ufscar.dc.dsw.pescd.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "planos_trabalho")
public class PlanoTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // estratégia mais segura
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

    // Parecer começa nulo, é preenchido depois
    @Column(columnDefinition = "TEXT")
    private String parecer;

    @ManyToOne // vários planos -> 1 professor supervisor
    @JoinColumn(name = "professor_supervisor_id", nullable = false) // coluna extra com id do professor
    private Usuario professorSupervisor;

    @OneToOne // 1 inscrição/aluno -> 1 plano
    @JoinColumn(name = "inscricao_id", nullable = false) // coluna extra com id da inscrição
    private Inscricao inscricao;
 
    // Parecer fica de fora do construtor principal, é inserido depois
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

    // Construtor necessário para o Hibernate
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