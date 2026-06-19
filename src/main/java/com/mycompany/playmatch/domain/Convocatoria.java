package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Convocatoria.
 */
@Document(collection = "convocatoria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Convocatoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 150)
    @Field("titulo")
    private String titulo;

    @Field("descripcion")
    private String descripcion;

    @NotNull
    @Field("fecha_publicacion")
    private Instant fechaPublicacion;

    @Field("fecha_inicio_inscripcion")
    private LocalDate fechaInicioInscripcion;

    @Field("fecha_fin_inscripcion")
    private LocalDate fechaFinInscripcion;

    @Field("cupos")
    private Integer cupos;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

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

    public Convocatoria id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Convocatoria titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Convocatoria descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public Convocatoria fechaPublicacion(Instant fechaPublicacion) {
        this.setFechaPublicacion(fechaPublicacion);
        return this;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaInicioInscripcion() {
        return this.fechaInicioInscripcion;
    }

    public Convocatoria fechaInicioInscripcion(LocalDate fechaInicioInscripcion) {
        this.setFechaInicioInscripcion(fechaInicioInscripcion);
        return this;
    }

    public void setFechaInicioInscripcion(LocalDate fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public LocalDate getFechaFinInscripcion() {
        return this.fechaFinInscripcion;
    }

    public Convocatoria fechaFinInscripcion(LocalDate fechaFinInscripcion) {
        this.setFechaFinInscripcion(fechaFinInscripcion);
        return this;
    }

    public void setFechaFinInscripcion(LocalDate fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public Integer getCupos() {
        return this.cupos;
    }

    public Convocatoria cupos(Integer cupos) {
        this.setCupos(cupos);
        return this;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Convocatoria estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Convocatoria torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Convocatoria)) {
            return false;
        }
        return getId() != null && getId().equals(((Convocatoria) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Convocatoria{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", fechaInicioInscripcion='" + getFechaInicioInscripcion() + "'" +
            ", fechaFinInscripcion='" + getFechaFinInscripcion() + "'" +
            ", cupos=" + getCupos() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
