package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.ModalidadDeporte;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Categoria.
 */
@Document(collection = "categoria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    @Field("nombre")
    private String nombre;

    @NotNull
    @Field("modalidad")
    private ModalidadDeporte modalidad;

    @Field("descripcion")
    private String descripcion;

    @Field("fecha_creacion")
    private Instant fechaCreacion;

    @Field("fecha_actualizacion")
    private Instant fechaActualizacion;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("torneos")
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
    private Set<Torneo> torneoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Categoria id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Categoria nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ModalidadDeporte getModalidad() {
        return this.modalidad;
    }

    public Categoria modalidad(ModalidadDeporte modalidad) {
        this.setModalidad(modalidad);
        return this;
    }

    public void setModalidad(ModalidadDeporte modalidad) {
        this.modalidad = modalidad;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Categoria descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Categoria fechaCreacion(Instant fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public Categoria fechaActualizacion(Instant fechaActualizacion) {
        this.setFechaActualizacion(fechaActualizacion);
        return this;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Categoria estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Set<Torneo> getTorneoses() {
        return this.torneoses;
    }

    public void setTorneoses(Set<Torneo> torneos) {
        if (this.torneoses != null) {
            this.torneoses.forEach(i -> i.setCategoria(null));
        }
        if (torneos != null) {
            torneos.forEach(i -> i.setCategoria(this));
        }
        this.torneoses = torneos;
    }

    public Categoria torneoses(Set<Torneo> torneos) {
        this.setTorneoses(torneos);
        return this;
    }

    public Categoria addTorneos(Torneo torneo) {
        this.torneoses.add(torneo);
        torneo.setCategoria(this);
        return this;
    }

    public Categoria removeTorneos(Torneo torneo) {
        this.torneoses.remove(torneo);
        torneo.setCategoria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return getId() != null && getId().equals(((Categoria) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", modalidad='" + getModalidad() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
