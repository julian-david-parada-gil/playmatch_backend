package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.MensajeGrupo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MensajeGrupoDTO implements Serializable {

    private String id;

    private String contenido;

    @NotNull
    private Instant fechaPublicacion;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private GrupoDTO grupo;

    @NotNull
    private CuentaDTO autor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    public CuentaDTO getAutor() {
        return autor;
    }

    public void setAutor(CuentaDTO autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensajeGrupoDTO)) {
            return false;
        }

        MensajeGrupoDTO mensajeGrupoDTO = (MensajeGrupoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mensajeGrupoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MensajeGrupoDTO{" +
            "id='" + getId() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", grupo=" + getGrupo() +
            ", autor=" + getAutor() +
            "}";
    }
}
