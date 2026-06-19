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
 * A MiembroGrupo.
 */
@Document(collection = "miembro_grupo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MiembroGrupo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("fecha_ingreso")
    private Instant fechaIngreso;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @NotNull
    @Field("es_administrador")
    private Boolean esAdministrador;

    @DBRef
    @Field("grupo")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo grupo;

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

    public MiembroGrupo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getFechaIngreso() {
        return this.fechaIngreso;
    }

    public MiembroGrupo fechaIngreso(Instant fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public MiembroGrupo estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Boolean getEsAdministrador() {
        return this.esAdministrador;
    }

    public MiembroGrupo esAdministrador(Boolean esAdministrador) {
        this.setEsAdministrador(esAdministrador);
        return this;
    }

    public void setEsAdministrador(Boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public MiembroGrupo grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public Cuenta getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Cuenta cuenta) {
        this.usuario = cuenta;
    }

    public MiembroGrupo usuario(Cuenta cuenta) {
        this.setUsuario(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MiembroGrupo)) {
            return false;
        }
        return getId() != null && getId().equals(((MiembroGrupo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MiembroGrupo{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", estado='" + getEstado() + "'" +
            ", esAdministrador='" + getEsAdministrador() + "'" +
            "}";
    }
}
