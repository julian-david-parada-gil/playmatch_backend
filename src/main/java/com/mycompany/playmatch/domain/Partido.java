package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoPartido;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Partido.
 */
@Document(collection = "partido")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("fecha_hora")
    private ZonedDateTime fechaHora;

    @NotNull
    @Size(max = 150)
    @Field("lugar")
    private String lugar;

    @NotNull
    @Field("tiempo_minutos")
    private Integer tiempoMinutos;

    @Field("marcador_local")
    private Integer marcadorLocal;

    @Field("marcador_visitante")
    private Integer marcadorVisitante;

    @NotNull
    @Field("estado")
    private EstadoPartido estado;

    @DBRef
    @Field("equipolocal")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo equipolocal;

    @DBRef
    @Field("equipovisitante")
    @JsonIgnoreProperties(value = { "miembroses", "mensajeses", "encuestases" }, allowSetters = true)
    private Grupo equipovisitante;

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

    public Partido id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getFechaHora() {
        return this.fechaHora;
    }

    public Partido fechaHora(ZonedDateTime fechaHora) {
        this.setFechaHora(fechaHora);
        return this;
    }

    public void setFechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLugar() {
        return this.lugar;
    }

    public Partido lugar(String lugar) {
        this.setLugar(lugar);
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getTiempoMinutos() {
        return this.tiempoMinutos;
    }

    public Partido tiempoMinutos(Integer tiempoMinutos) {
        this.setTiempoMinutos(tiempoMinutos);
        return this;
    }

    public void setTiempoMinutos(Integer tiempoMinutos) {
        this.tiempoMinutos = tiempoMinutos;
    }

    public Integer getMarcadorLocal() {
        return this.marcadorLocal;
    }

    public Partido marcadorLocal(Integer marcadorLocal) {
        this.setMarcadorLocal(marcadorLocal);
        return this;
    }

    public void setMarcadorLocal(Integer marcadorLocal) {
        this.marcadorLocal = marcadorLocal;
    }

    public Integer getMarcadorVisitante() {
        return this.marcadorVisitante;
    }

    public Partido marcadorVisitante(Integer marcadorVisitante) {
        this.setMarcadorVisitante(marcadorVisitante);
        return this;
    }

    public void setMarcadorVisitante(Integer marcadorVisitante) {
        this.marcadorVisitante = marcadorVisitante;
    }

    public EstadoPartido getEstado() {
        return this.estado;
    }

    public Partido estado(EstadoPartido estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }

    public Grupo getEquipolocal() {
        return this.equipolocal;
    }

    public void setEquipolocal(Grupo grupo) {
        this.equipolocal = grupo;
    }

    public Partido equipolocal(Grupo grupo) {
        this.setEquipolocal(grupo);
        return this;
    }

    public Grupo getEquipovisitante() {
        return this.equipovisitante;
    }

    public void setEquipovisitante(Grupo grupo) {
        this.equipovisitante = grupo;
    }

    public Partido equipovisitante(Grupo grupo) {
        this.setEquipovisitante(grupo);
        return this;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Partido torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partido)) {
            return false;
        }
        return getId() != null && getId().equals(((Partido) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partido{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", tiempoMinutos=" + getTiempoMinutos() +
            ", marcadorLocal=" + getMarcadorLocal() +
            ", marcadorVisitante=" + getMarcadorVisitante() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
