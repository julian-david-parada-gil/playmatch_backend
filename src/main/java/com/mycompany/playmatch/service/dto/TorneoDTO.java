package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoTorneo;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Torneo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TorneoDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 120)
    private String nombre;

    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private ZonedDateTime horaInicio;

    @NotNull
    @Size(max = 200)
    private String ubicacion;

    private byte[] reglamento;

    private String reglamentoContentType;

    @NotNull
    private Integer cupoMaximoEquipos;

    private Integer cupoMaximoJugadores;

    @NotNull
    private EstadoTorneo estado;

    @NotNull
    private CategoriaDTO categoria;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ZonedDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(ZonedDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public byte[] getReglamento() {
        return reglamento;
    }

    public void setReglamento(byte[] reglamento) {
        this.reglamento = reglamento;
    }

    public String getReglamentoContentType() {
        return reglamentoContentType;
    }

    public void setReglamentoContentType(String reglamentoContentType) {
        this.reglamentoContentType = reglamentoContentType;
    }

    public Integer getCupoMaximoEquipos() {
        return cupoMaximoEquipos;
    }

    public void setCupoMaximoEquipos(Integer cupoMaximoEquipos) {
        this.cupoMaximoEquipos = cupoMaximoEquipos;
    }

    public Integer getCupoMaximoJugadores() {
        return cupoMaximoJugadores;
    }

    public void setCupoMaximoJugadores(Integer cupoMaximoJugadores) {
        this.cupoMaximoJugadores = cupoMaximoJugadores;
    }

    public EstadoTorneo getEstado() {
        return estado;
    }

    public void setEstado(EstadoTorneo estado) {
        this.estado = estado;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TorneoDTO)) {
            return false;
        }

        TorneoDTO torneoDTO = (TorneoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, torneoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TorneoDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", reglamento='" + getReglamento() + "'" +
            ", cupoMaximoEquipos=" + getCupoMaximoEquipos() +
            ", cupoMaximoJugadores=" + getCupoMaximoJugadores() +
            ", estado='" + getEstado() + "'" +
            ", categoria=" + getCategoria() +
            "}";
    }
}
