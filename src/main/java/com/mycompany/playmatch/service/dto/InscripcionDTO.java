package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoSolicitud;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Inscripcion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InscripcionDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 30)
    private String codigo;

    @NotNull
    private Instant fechaInscripcion;

    @NotNull
    private EstadoSolicitud estado;

    private String observaciones;

    @NotNull
    private TorneoDTO torneo;

    @NotNull
    private CuentaDTO usuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Instant getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Instant fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public TorneoDTO getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoDTO torneo) {
        this.torneo = torneo;
    }

    public CuentaDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(CuentaDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscripcionDTO)) {
            return false;
        }

        InscripcionDTO inscripcionDTO = (InscripcionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inscripcionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscripcionDTO{" +
            "id='" + getId() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", fechaInscripcion='" + getFechaInscripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", torneo=" + getTorneo() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
