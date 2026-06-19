package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Calificacion.
 */
@Document(collection = "calificacion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Calificacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("puntaje")
    private Integer puntaje;

    @Size(max = 255)
    @Field("comentario")
    private String comentario;

    @NotNull
    @Field("fecha_calificacion")
    private Instant fechaCalificacion;

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

    @DBRef
    @Field("autor")
    @JsonIgnoreProperties(
        value = {
            "user",
            "gruposes",
            "inscripcioneses",
            "mensajeses",
            "calificacioneses",
            "noticiases",
            "notificacioneses",
            "tipoDocumento",
        },
        allowSetters = true
    )
    private Cuenta autor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Calificacion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPuntaje() {
        return this.puntaje;
    }

    public Calificacion puntaje(Integer puntaje) {
        this.setPuntaje(puntaje);
        return this;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return this.comentario;
    }

    public Calificacion comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFechaCalificacion() {
        return this.fechaCalificacion;
    }

    public Calificacion fechaCalificacion(Instant fechaCalificacion) {
        this.setFechaCalificacion(fechaCalificacion);
        return this;
    }

    public void setFechaCalificacion(Instant fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Calificacion estado(EstadoGeneral estado) {
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

    public Calificacion torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    public Cuenta getAutor() {
        return this.autor;
    }

    public void setAutor(Cuenta cuenta) {
        this.autor = cuenta;
    }

    public Calificacion autor(Cuenta cuenta) {
        this.setAutor(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calificacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Calificacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calificacion{" +
            "id=" + getId() +
            ", puntaje=" + getPuntaje() +
            ", comentario='" + getComentario() + "'" +
            ", fechaCalificacion='" + getFechaCalificacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
