package com.mycompany.playmatch.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.TablaPosicion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TablaPosicionDTO implements Serializable {

    private String id;

    @NotNull
    private Integer puntos;

    @NotNull
    private Integer partidosJugados;

    @NotNull
    private Integer partidosGanados;

    @NotNull
    private Integer partidosEmpatados;

    @NotNull
    private Integer partidosPerdidos;

    @NotNull
    private Integer golesFavor;

    @NotNull
    private Integer golesContra;

    @NotNull
    private Integer diferenciaGoles;

    @NotNull
    private GrupoDTO grupo;

    @NotNull
    private TorneoDTO torneo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(Integer partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public Integer getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(Integer partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public Integer getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(Integer partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public Integer getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }

    public Integer getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }

    public Integer getDiferenciaGoles() {
        return diferenciaGoles;
    }

    public void setDiferenciaGoles(Integer diferenciaGoles) {
        this.diferenciaGoles = diferenciaGoles;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
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
        if (!(o instanceof TablaPosicionDTO)) {
            return false;
        }

        TablaPosicionDTO tablaPosicionDTO = (TablaPosicionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tablaPosicionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TablaPosicionDTO{" +
            "id='" + getId() + "'" +
            ", puntos=" + getPuntos() +
            ", partidosJugados=" + getPartidosJugados() +
            ", partidosGanados=" + getPartidosGanados() +
            ", partidosEmpatados=" + getPartidosEmpatados() +
            ", partidosPerdidos=" + getPartidosPerdidos() +
            ", golesFavor=" + getGolesFavor() +
            ", golesContra=" + getGolesContra() +
            ", diferenciaGoles=" + getDiferenciaGoles() +
            ", grupo=" + getGrupo() +
            ", torneo=" + getTorneo() +
            "}";
    }
}
