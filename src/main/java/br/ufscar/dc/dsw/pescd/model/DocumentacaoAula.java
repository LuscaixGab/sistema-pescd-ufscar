package br.ufscar.dc.dsw.pescd.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "documentacoes_aulas")
public class DocumentacaoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nomeInstituicao;

    @Column(nullable = false)
    private String nomeDisciplina;

    @Column(nullable = false)
    private String cursoDisciplina;

    @Column(nullable = false)
    private Integer cargaHoraria; // em horas

    @Column(nullable = false)
    private String arquivoDocumentacao; // Caminho do PDF

    // Dados preenchidos depois pelo Professor Responsável (PR.02)
    @Column(columnDefinition = "TEXT")
    private String parecer;

    @Column
    private Integer indicadorFrequencia; // 0 a 100%

    @Column(length = 1)
    private String nota; // Pode ser A, B, C, D ou E

    @OneToOne
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;

    // Construtor com os dados do envio (AL.03).
    // Os campos do professor ficam de fora pois começam nulos.
    public DocumentacaoAula(UUID id, String nomeInstituicao, String nomeDisciplina, String cursoDisciplina,
                            Integer cargaHoraria, String arquivoDocumentacao, Inscricao inscricao) {
        this.id = id;
        this.nomeInstituicao = nomeInstituicao;
        this.nomeDisciplina = nomeDisciplina;
        this.cursoDisciplina = cursoDisciplina;
        this.cargaHoraria = cargaHoraria;
        this.arquivoDocumentacao = arquivoDocumentacao;
        this.inscricao = inscricao;
    }

    protected DocumentacaoAula() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
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

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getArquivoDocumentacao() {
        return arquivoDocumentacao;
    }

    public void setArquivoDocumentacao(String arquivoDocumentacao) {
        this.arquivoDocumentacao = arquivoDocumentacao;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public Integer getIndicadorFrequencia() {
        return indicadorFrequencia;
    }

    public void setIndicadorFrequencia(Integer indicadorFrequencia) {
        this.indicadorFrequencia = indicadorFrequencia;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    
}