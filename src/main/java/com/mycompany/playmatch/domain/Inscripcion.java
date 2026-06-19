package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoSolicitud;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Inscripcion.
 */
@Document(collection = "inscripcion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inscripcion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("codigo")
    private String codigo;

    @NotNull
    @Field("fecha_inscripcion")
    private Instant fechaInscripcion;

    @NotNull
    @Field("estado")
    private EstadoSolicitud estado;

    @Field("observaciones")
    private String observaciones;

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
    @Field("usuario")
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
    private Cuenta usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Inscripcion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Inscripcion codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Instant getFechaInscripcion() {
        return this.fechaInscripcion;
    }

    public Inscripcion fechaInscripcion(Instant fechaInscripcion) {
        this.setFechaInscripcion(fechaInscripcion);
        return this;
    }

    public void setFechaInscripcion(Instant fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoSolicitud getEstado() {
        return this.estado;
    }

    public Inscripcion estado(EstadoSolicitud estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Inscripcion observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Inscripcion torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    public Cuenta getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Cuenta cuenta) {
        this.usuario = cuenta;
    }

    public Inscripcion usuario(Cuenta cuenta) {
        this.setUsuario(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscripcion)) {
            return false;
        }
        return getId() != null && getId().equals(((Inscripcion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inscripcion{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", fechaInscripcion='" + getFechaInscripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
