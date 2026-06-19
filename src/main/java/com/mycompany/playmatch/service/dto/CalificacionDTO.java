package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Calificacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalificacionDTO implements Serializable {

    private String id;

    @NotNull
    private Integer puntaje;

    @Size(max = 255)
    private String comentario;

    @NotNull
    private Instant fechaCalificacion;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private TorneoDTO torneo;

    @NotNull
    private CuentaDTO autor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(Instant fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public TorneoDTO getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoDTO torneo) {
        this.torneo = torneo;
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
        if (!(o instanceof CalificacionDTO)) {
            return false;
        }

        CalificacionDTO calificacionDTO = (CalificacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calificacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalificacionDTO{" +
            "id='" + getId() + "'" +
            ", puntaje=" + getPuntaje() +
            ", comentario='" + getComentario() + "'" +
            ", fechaCalificacion='" + getFechaCalificacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", torneo=" + getTorneo() +
            ", autor=" + getAutor() +
            "}";
    }
}
