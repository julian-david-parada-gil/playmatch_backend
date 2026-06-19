package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoPartido;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Partido} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartidoDTO implements Serializable {

    private String id;

    @NotNull
    private ZonedDateTime fechaHora;

    @NotNull
    @Size(max = 150)
    private String lugar;

    @NotNull
    private Integer tiempoMinutos;

    private Integer marcadorLocal;

    private Integer marcadorVisitante;

    @NotNull
    private EstadoPartido estado;

    private GrupoDTO equipolocal;

    private GrupoDTO equipovisitante;

    @NotNull
    private TorneoDTO torneo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getTiempoMinutos() {
        return tiempoMinutos;
    }

    public void setTiempoMinutos(Integer tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public Integer getMarcadorLocal() {
        return marcadorLocal;
    }

    public void setMarcadorLocal(Integer marcadorLocal) {
        this.marcadorLocal = marcadorLocal;
    }

    public Integer getMarcadorVisitante() {
        return marcadorVisitante;
    }

    public void setMarcadorVisitante(Integer marcadorVisitante) {
        this.marcadorVisitante = marcadorVisitante;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }

    public GrupoDTO getEquipolocal() {
        return equipolocal;
    }

    public void setEquipolocal(GrupoDTO equipolocal) {
        this.equipolocal = equipolocal;
    }

    public GrupoDTO getEquipovisitante() {
        return equipovisitante;
    }

    public void setEquipovisitante(GrupoDTO equipovisitante) {
        this.equipovisitante = equipovisitante;
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
        if (!(o instanceof PartidoDTO)) {
            return false;
        }

        PartidoDTO partidoDTO = (PartidoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partidoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartidoDTO{" +
            "id='" + getId() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", tiempoMinutos=" + getTiempoMinutos() +
            ", marcadorLocal=" + getMarcadorLocal() +
            ", marcadorVisitante=" + getMarcadorVisitante() +
            ", estado='" + getEstado() + "'" +
            ", equipolocal=" + getEquipolocal() +
            ", equipovisitante=" + getEquipovisitante() +
            ", torneo=" + getTorneo() +
            "}";
    }
}
