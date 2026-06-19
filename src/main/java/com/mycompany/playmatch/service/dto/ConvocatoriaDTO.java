package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Convocatoria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConvocatoriaDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 150)
    private String titulo;

    private String descripcion;

    @NotNull
    private Instant fechaPublicacion;

    private LocalDate fechaInicioInscripcion;

    private LocalDate fechaFinInscripcion;

    private Integer cupos;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private TorneoDTO torneo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(LocalDate fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public LocalDate getFechaFinInscripcion() {
        return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(LocalDate fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConvocatoriaDTO)) {
            return false;
        }

        ConvocatoriaDTO convocatoriaDTO = (ConvocatoriaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, convocatoriaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConvocatoriaDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", fechaInicioInscripcion='" + getFechaInicioInscripcion() + "'" +
            ", fechaFinInscripcion='" + getFechaFinInscripcion() + "'" +
            ", cupos=" + getCupos() +
            ", estado='" + getEstado() + "'" +
            ", torneo=" + getTorneo() +
            "}";
    }
}
