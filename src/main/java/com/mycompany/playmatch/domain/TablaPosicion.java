package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A TablaPosicion.
 */
@Document(collection = "tabla_posicion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TablaPosicion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("puntos")
    private Integer puntos;

    @NotNull
    @Field("partidos_jugados")
    private Integer partidosJugados;

    @NotNull
    @Field("partidos_ganados")
    private Integer partidosGanados;

    @NotNull
    @Field("partidos_empatados")
    private Integer partidosEmpatados;

    @NotNull
    @Field("partidos_perdidos")
    private Integer partidosPerdidos;

    @NotNull
    @Field("goles_favor")
    private Integer golesFavor;

    @NotNull
    @Field("goles_contra")
    private Integer golesContra;

    @NotNull
    @Field("diferencia_goles")
    private Integer diferenciaGoles;

    @DBRef
    @Field("grupo")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo grupo;

    @DBRef
    @Field("torneo")
    @JsonIgnoreProperties(
        value = {
            "inscripcioneses",
            "partidoses",
            "tablaPosicioneses",
            "encuestases",
            "calificacioneses",
            "convocatoriases",
            "eventosCalendarios",
            "categoria",
        },
        allowSetters = true
    )
    private Torneo torneo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public TablaPosicion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPuntos() {
        return this.puntos;
    }

    public TablaPosicion puntos(Integer puntos) {
        this.setPuntos(puntos);
        return this;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getPartidosJugados() {
        return this.partidosJugados;
    }

    public TablaPosicion partidosJugados(Integer partidosJugados) {
        this.setPartidosJugados(partidosJugados);
        return this;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getPartidosGanados() {
        return this.partidosGanados;
    }

    public TablaPosicion partidosGanados(Integer partidosGanados) {
        this.setPartidosGanados(partidosGanados);
        return this;
    }

    public void setPartidosGanados(Integer partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public Integer getPartidosEmpatados() {
        return this.partidosEmpatados;
    }

    public TablaPosicion partidosEmpatados(Integer partidosEmpatados) {
        this.setPartidosEmpatados(partidosEmpatados);
        return this;
    }

    public void setPartidosEmpatados(Integer partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public Integer getPartidosPerdidos() {
        return this.partidosPerdidos;
    }

    public TablaPosicion partidosPerdidos(Integer partidosPerdidos) {
        this.setPartidosPerdidos(partidosPerdidos);
        return this;
    }

    public void setPartidosPerdidos(Integer partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public Integer getGolesFavor() {
        return this.golesFavor;
    }

    public TablaPosicion golesFavor(Integer golesFavor) {
        this.setGolesFavor(golesFavor);
        return this;
    }

    public void setGolesFavor(Integer golesFavor) {
        this.golesFavor = golesFavor;
    }

    public Integer getGolesContra() {
        return this.golesContra;
    }

    public TablaPosicion golesContra(Integer golesContra) {
        this.setGolesContra(golesContra);
        return this;
    }

    public void setGolesContra(Integer golesContra) {
        this.golesContra = golesContra;
    }

    public Integer getDiferenciaGoles() {
        return this.diferenciaGoles;
    }

    public TablaPosicion diferenciaGoles(Integer diferenciaGoles) {
        this.setDiferenciaGoles(diferenciaGoles);
        return this;
    }

    public void setDiferenciaGoles(Integer diferenciaGoles) {
        this.diferenciaGoles = diferenciaGoles;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public TablaPosicion grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public TablaPosicion torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TablaPosicion)) {
            return false;
        }
        return getId() != null && getId().equals(((TablaPosicion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TablaPosicion{" +
            "id=" + getId() +
            ", puntos=" + getPuntos() +
            ", partidosJugados=" + getPartidosJugados() +
            ", partidosGanados=" + getPartidosGanados() +
            ", partidosEmpatados=" + getPartidosEmpatados() +
            ", partidosPerdidos=" + getPartidosPerdidos() +
            ", golesFavor=" + getGolesFavor() +
            ", golesContra=" + getGolesContra() +
            ", diferenciaGoles=" + getDiferenciaGoles() +
            "}";
    }
}
