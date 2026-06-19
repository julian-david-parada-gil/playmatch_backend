package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoEncuesta;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Encuesta.
 */
@Document(collection = "encuesta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Encuesta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("titulo")
    private String titulo;

    @NotNull
    @Size(max = 255)
    @Field("opcion_1")
    private String opcion1;

    @NotNull
    @Size(max = 255)
    @Field("opcion_2")
    private String opcion2;

    @Size(max = 255)
    @Field("opcion_3")
    private String opcion3;

    @Size(max = 255)
    @Field("opcion_4")
    private String opcion4;

    @Field("votos_opcion_1")
    private Integer votosOpcion1;

    @Field("votos_opcion_2")
    private Integer votosOpcion2;

    @Field("votos_opcion_3")
    private Integer votosOpcion3;

    @Field("votos_opcion_4")
    private Integer votosOpcion4;

    @Field("fecha_inicio")
    private Instant fechaInicio;

    @Field("fecha_fin")
    private Instant fechaFin;

    @NotNull
    @Field("estado")
    private EstadoEncuesta estado;

    @DBRef
    @Field("grupo")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo grupo;

    @DBRef
    @Field("torneo")
    @JsonIgnoreProperties(
        value = {
            "inscripcioneses",
            "partidoses",
            "tablaPosicioneses",
            "encuestases",
            "calificacioneses",
            "convocatoriases",
            "eventosCalendarios",
            "categoria",
        },
        allowSetters = true
    )
    private Torneo torneo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Encuesta id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Encuesta titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getOpcion1() {
        return this.opcion1;
    }

    public Encuesta opcion1(String opcion1) {
        this.setOpcion1(opcion1);
        return this;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return this.opcion2;
    }

    public Encuesta opcion2(String opcion2) {
        this.setOpcion2(opcion2);
        return this;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return this.opcion3;
    }

    public Encuesta opcion3(String opcion3) {
        this.setOpcion3(opcion3);
        return this;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return this.opcion4;
    }

    public Encuesta opcion4(String opcion4) {
        this.setOpcion4(opcion4);
        return this;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public Integer getVotosOpcion1() {
        return this.votosOpcion1;
    }

    public Encuesta votosOpcion1(Integer votosOpcion1) {
        this.setVotosOpcion1(votosOpcion1);
        return this;
    }

    public void setVotosOpcion1(Integer votosOpcion1) {
        this.votosOpcion1 = votosOpcion1;
    }

    public Integer getVotosOpcion2() {
        return this.votosOpcion2;
    }

    public Encuesta votosOpcion2(Integer votosOpcion2) {
        this.setVotosOpcion2(votosOpcion2);
        return this;
    }

    public void setVotosOpcion2(Integer votosOpcion2) {
        this.votosOpcion2 = votosOpcion2;
    }

    public Integer getVotosOpcion3() {
        return this.votosOpcion3;
    }

    public Encuesta votosOpcion3(Integer votosOpcion3) {
        this.setVotosOpcion3(votosOpcion3);
        return this;
    }

    public void setVotosOpcion3(Integer votosOpcion3) {
        this.votosOpcion3 = votosOpcion3;
    }

    public Integer getVotosOpcion4() {
        return this.votosOpcion4;
    }

    public Encuesta votosOpcion4(Integer votosOpcion4) {
        this.setVotosOpcion4(votosOpcion4);
        return this;
    }

    public void setVotosOpcion4(Integer votosOpcion4) {
        this.votosOpcion4 = votosOpcion4;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public Encuesta fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return this.fechaFin;
    }

    public Encuesta fechaFin(Instant fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoEncuesta getEstado() {
        return this.estado;
    }

    public Encuesta estado(EstadoEncuesta estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoEncuesta estado) {
        this.estado = estado;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Encuesta grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Encuesta torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Encuesta)) {
            return false;
        }
        return getId() != null && getId().equals(((Encuesta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Encuesta{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", opcion1='" + getOpcion1() + "'" +
            ", opcion2='" + getOpcion2() + "'" +
            ", opcion3='" + getOpcion3() + "'" +
            ", opcion4='" + getOpcion4() + "'" +
            ", votosOpcion1=" + getVotosOpcion1() +
            ", votosOpcion2=" + getVotosOpcion2() +
            ", votosOpcion3=" + getVotosOpcion3() +
            ", votosOpcion4=" + getVotosOpcion4() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
