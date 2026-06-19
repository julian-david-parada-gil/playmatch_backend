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
 * A MensajeGrupo.
 */
@Document(collection = "mensaje_grupo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MensajeGrupo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("contenido")
    private String contenido;

    @NotNull
    @Field("fecha_publicacion")
    private Instant fechaPublicacion;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("grupo")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo grupo;

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

    public MensajeGrupo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return this.contenido;
    }

    public MensajeGrupo contenido(String contenido) {
        this.setContenido(contenido);
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public MensajeGrupo fechaPublicacion(Instant fechaPublicacion) {
        this.setFechaPublicacion(fechaPublicacion);
        return this;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public MensajeGrupo estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public MensajeGrupo grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public Cuenta getAutor() {
        return this.autor;
    }

    public void setAutor(Cuenta cuenta) {
        this.autor = cuenta;
    }

    public MensajeGrupo autor(Cuenta cuenta) {
        this.setAutor(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensajeGrupo)) {
            return false;
        }
        return getId() != null && getId().equals(((MensajeGrupo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MensajeGrupo{" +
            "id=" + getId() +
            ", contenido='" + getContenido() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
