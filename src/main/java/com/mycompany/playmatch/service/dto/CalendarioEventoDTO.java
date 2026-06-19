package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoEventoCalendario;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.CalendarioEvento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendarioEventoDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 120)
    private String titulo;

    private String descripcion;

    private byte[] calendario;

    private String calendarioContentType;

    @NotNull
    private ZonedDateTime fechaEvento;

    @NotNull
    private TipoEventoCalendario tipo;

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

    public byte[] getCalendario() {
        return calendario;
    }

    public void setCalendario(byte[] calendario) {
        this.calendario = calendario;
    }

    public String getCalendarioContentType() {
        return calendarioContentType;
    }

    public void setCalendarioContentType(String calendarioContentType) {
        this.calendarioContentType = calendarioContentType;
    }

    public ZonedDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(ZonedDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public TipoEventoCalendario getTipo() {
        return tipo;
    }

    public void setTipo(TipoEventoCalendario tipo) {
        this.tipo = tipo;
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
        if (!(o instanceof CalendarioEventoDTO)) {
            return false;
        }

        CalendarioEventoDTO calendarioEventoDTO = (CalendarioEventoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calendarioEventoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarioEventoDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", calendario='" + getCalendario() + "'" +
            ", fechaEvento='" + getFechaEvento() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", torneo=" + getTorneo() +
            "}";
    }
}
