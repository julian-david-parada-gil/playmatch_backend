package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoEventoCalendario;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A CalendarioEvento.
 */
@Document(collection = "calendario_evento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendarioEvento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 120)
    @Field("titulo")
    private String titulo;

    @Field("descripcion")
    private String descripcion;

    @Field("calendario")
    private byte[] calendario;

    @Field("calendario_content_type")
    private String calendarioContentType;

    @NotNull
    @Field("fecha_evento")
    private ZonedDateTime fechaEvento;

    @NotNull
    @Field("tipo")
    private TipoEventoCalendario tipo;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

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

    public CalendarioEvento id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public CalendarioEvento titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public CalendarioEvento descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getCalendario() {
        return this.calendario;
    }

    public CalendarioEvento calendario(byte[] calendario) {
        this.setCalendario(calendario);
        return this;
    }

    public void setCalendario(byte[] calendario) {
        this.calendario = calendario;
    }

    public String getCalendarioContentType() {
        return this.calendarioContentType;
    }

    public CalendarioEvento calendarioContentType(String calendarioContentType) {
        this.calendarioContentType = calendarioContentType;
        return this;
    }

    public void setCalendarioContentType(String calendarioContentType) {
        this.calendarioContentType = calendarioContentType;
    }

    public ZonedDateTime getFechaEvento() {
        return this.fechaEvento;
    }

    public CalendarioEvento fechaEvento(ZonedDateTime fechaEvento) {
        this.setFechaEvento(fechaEvento);
        return this;
    }

    public void setFechaEvento(ZonedDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public TipoEventoCalendario getTipo() {
        return this.tipo;
    }

    public CalendarioEvento tipo(TipoEventoCalendario tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoEventoCalendario tipo) {
        this.tipo = tipo;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public CalendarioEvento estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Torneo getTorneo() {
        return this.torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public CalendarioEvento torneo(Torneo torneo) {
        this.setTorneo(torneo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarioEvento)) {
            return false;
        }
        return getId() != null && getId().equals(((CalendarioEvento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarioEvento{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", calendario='" + getCalendario() + "'" +
            ", calendarioContentType='" + getCalendarioContentType() + "'" +
            ", fechaEvento='" + getFechaEvento() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
