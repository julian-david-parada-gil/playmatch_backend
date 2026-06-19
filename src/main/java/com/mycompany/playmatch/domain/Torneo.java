package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoTorneo;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Torneo.
 */
@Document(collection = "torneo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Torneo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 120)
    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @NotNull
    @Field("fecha_inicio")
    private LocalDate fechaInicio;

    @Field("fecha_fin")
    private LocalDate fechaFin;

    @Field("hora_inicio")
    private ZonedDateTime horaInicio;

    @NotNull
    @Size(max = 200)
    @Field("ubicacion")
    private String ubicacion;

    @Field("reglamento")
    private byte[] reglamento;

    @Field("reglamento_content_type")
    private String reglamentoContentType;

    @NotNull
    @Field("cupo_maximo_equipos")
    private Integer cupoMaximoEquipos;

    @Field("cupo_maximo_jugadores")
    private Integer cupoMaximoJugadores;

    @NotNull
    @Field("estado")
    private EstadoTorneo estado;

    @DBRef
    @Field("inscripciones")
    @JsonIgnoreProperties(value = { "torneo", "usuario" }, allowSetters = true)
    private Set<Inscripcion> inscripcioneses = new HashSet<>();

    @DBRef
    @Field("partidos")
    @JsonIgnoreProperties(value = { "equipolocal", "equipovisitante", "torneo" }, allowSetters = true)
    private Set<Partido> partidoses = new HashSet<>();

    @DBRef
    @Field("tablaPosiciones")
    @JsonIgnoreProperties(value = { "grupo", "torneo" }, allowSetters = true)
    private Set<TablaPosicion> tablaPosicioneses = new HashSet<>();

    @DBRef
    @Field("encuestas")
    @JsonIgnoreProperties(value = { "grupo", "torneo" }, allowSetters = true)
    private Set<Encuesta> encuestases = new HashSet<>();

    @DBRef
    @Field("calificaciones")
    @JsonIgnoreProperties(value = { "torneo", "autor" }, allowSetters = true)
    private Set<Calificacion> calificacioneses = new HashSet<>();

    @DBRef
    @Field("convocatorias")
    @JsonIgnoreProperties(value = { "torneo" }, allowSetters = true)
    private Set<Convocatoria> convocatoriases = new HashSet<>();

    @DBRef
    @Field("eventosCalendario")
    @JsonIgnoreProperties(value = { "torneo" }, allowSetters = true)
    private Set<CalendarioEvento> eventosCalendarios = new HashSet<>();

    @DBRef
    @Field("categoria")
    @JsonIgnoreProperties(value = { "torneoses" }, allowSetters = true)
    private Categoria categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Torneo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Torneo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Torneo descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Torneo fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Torneo fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ZonedDateTime getHoraInicio() {
        return this.horaInicio;
    }

    public Torneo horaInicio(ZonedDateTime horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(ZonedDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public Torneo ubicacion(String ubicacion) {
        this.setUbicacion(ubicacion);
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public byte[] getReglamento() {
        return this.reglamento;
    }

    public Torneo reglamento(byte[] reglamento) {
        this.setReglamento(reglamento);
        return this;
    }

    public void setReglamento(byte[] reglamento) {
        this.reglamento = reglamento;
    }

    public String getReglamentoContentType() {
        return this.reglamentoContentType;
    }

    public Torneo reglamentoContentType(String reglamentoContentType) {
        this.reglamentoContentType = reglamentoContentType;
        return this;
    }

    public void setReglamentoContentType(String reglamentoContentType) {
        this.reglamentoContentType = reglamentoContentType;
    }

    public Integer getCupoMaximoEquipos() {
        return this.cupoMaximoEquipos;
    }

    public Torneo cupoMaximoEquipos(Integer cupoMaximoEquipos) {
        this.setCupoMaximoEquipos(cupoMaximoEquipos);
        return this;
    }

    public void setCupoMaximoEquipos(Integer cupoMaximoEquipos) {
        this.cupoMaximoEquipos = cupoMaximoEquipos;
    }

    public Integer getCupoMaximoJugadores() {
        return this.cupoMaximoJugadores;
    }

    public Torneo cupoMaximoJugadores(Integer cupoMaximoJugadores) {
        this.setCupoMaximoJugadores(cupoMaximoJugadores);
        return this;
    }

    public void setCupoMaximoJugadores(Integer cupoMaximoJugadores) {
        this.cupoMaximoJugadores = cupoMaximoJugadores;
    }

    public EstadoTorneo getEstado() {
        return this.estado;
    }

    public Torneo estado(EstadoTorneo estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoTorneo estado) {
        this.estado = estado;
    }

    public Set<Inscripcion> getInscripcioneses() {
        return this.inscripcioneses;
    }

    public void setInscripcioneses(Set<Inscripcion> inscripcions) {
        if (this.inscripcioneses != null) {
            this.inscripcioneses.forEach(i -> i.setTorneo(null));
        }
        if (inscripcions != null) {
            inscripcions.forEach(i -> i.setTorneo(this));
        }
        this.inscripcioneses = inscripcions;
    }

    public Torneo inscripcioneses(Set<Inscripcion> inscripcions) {
        this.setInscripcioneses(inscripcions);
        return this;
    }

    public Torneo addInscripciones(Inscripcion inscripcion) {
        this.inscripcioneses.add(inscripcion);
        inscripcion.setTorneo(this);
        return this;
    }

    public Torneo removeInscripciones(Inscripcion inscripcion) {
        this.inscripcioneses.remove(inscripcion);
        inscripcion.setTorneo(null);
        return this;
    }

    public Set<Partido> getPartidoses() {
        return this.partidoses;
    }

    public void setPartidoses(Set<Partido> partidos) {
        if (this.partidoses != null) {
            this.partidoses.forEach(i -> i.setTorneo(null));
        }
        if (partidos != null) {
            partidos.forEach(i -> i.setTorneo(this));
        }
        this.partidoses = partidos;
    }

    public Torneo partidoses(Set<Partido> partidos) {
        this.setPartidoses(partidos);
        return this;
    }

    public Torneo addPartidos(Partido partido) {
        this.partidoses.add(partido);
        partido.setTorneo(this);
        return this;
    }

    public Torneo removePartidos(Partido partido) {
        this.partidoses.remove(partido);
        partido.setTorneo(null);
        return this;
    }

    public Set<TablaPosicion> getTablaPosicioneses() {
        return this.tablaPosicioneses;
    }

    public void setTablaPosicioneses(Set<TablaPosicion> tablaPosicions) {
        if (this.tablaPosicioneses != null) {
            this.tablaPosicioneses.forEach(i -> i.setTorneo(null));
        }
        if (tablaPosicions != null) {
            tablaPosicions.forEach(i -> i.setTorneo(this));
        }
        this.tablaPosicioneses = tablaPosicions;
    }

    public Torneo tablaPosicioneses(Set<TablaPosicion> tablaPosicions) {
        this.setTablaPosicioneses(tablaPosicions);
        return this;
    }

    public Torneo addTablaPosiciones(TablaPosicion tablaPosicion) {
        this.tablaPosicioneses.add(tablaPosicion);
        tablaPosicion.setTorneo(this);
        return this;
    }

    public Torneo removeTablaPosiciones(TablaPosicion tablaPosicion) {
        this.tablaPosicioneses.remove(tablaPosicion);
        tablaPosicion.setTorneo(null);
        return this;
    }

    public Set<Encuesta> getEncuestases() {
        return this.encuestases;
    }

    public void setEncuestases(Set<Encuesta> encuestas) {
        if (this.encuestases != null) {
            this.encuestases.forEach(i -> i.setTorneo(null));
        }
        if (encuestas != null) {
            encuestas.forEach(i -> i.setTorneo(this));
        }
        this.encuestases = encuestas;
    }

    public Torneo encuestases(Set<Encuesta> encuestas) {
        this.setEncuestases(encuestas);
        return this;
    }

    public Torneo addEncuestas(Encuesta encuesta) {
        this.encuestases.add(encuesta);
        encuesta.setTorneo(this);
        return this;
    }

    public Torneo removeEncuestas(Encuesta encuesta) {
        this.encuestases.remove(encuesta);
        encuesta.setTorneo(null);
        return this;
    }

    public Set<Calificacion> getCalificacioneses() {
        return this.calificacioneses;
    }

    public void setCalificacioneses(Set<Calificacion> calificacions) {
        if (this.calificacioneses != null) {
            this.calificacioneses.forEach(i -> i.setTorneo(null));
        }
        if (calificacions != null) {
            calificacions.forEach(i -> i.setTorneo(this));
        }
        this.calificacioneses = calificacions;
    }

    public Torneo calificacioneses(Set<Calificacion> calificacions) {
        this.setCalificacioneses(calificacions);
        return this;
    }

    public Torneo addCalificaciones(Calificacion calificacion) {
        this.calificacioneses.add(calificacion);
        calificacion.setTorneo(this);
        return this;
    }

    public Torneo removeCalificaciones(Calificacion calificacion) {
        this.calificacioneses.remove(calificacion);
        calificacion.setTorneo(null);
        return this;
    }

    public Set<Convocatoria> getConvocatoriases() {
        return this.convocatoriases;
    }

    public void setConvocatoriases(Set<Convocatoria> convocatorias) {
        if (this.convocatoriases != null) {
            this.convocatoriases.forEach(i -> i.setTorneo(null));
        }
        if (convocatorias != null) {
            convocatorias.forEach(i -> i.setTorneo(this));
        }
        this.convocatoriases = convocatorias;
    }

    public Torneo convocatoriases(Set<Convocatoria> convocatorias) {
        this.setConvocatoriases(convocatorias);
        return this;
    }

    public Torneo addConvocatorias(Convocatoria convocatoria) {
        this.convocatoriases.add(convocatoria);
        convocatoria.setTorneo(this);
        return this;
    }

    public Torneo removeConvocatorias(Convocatoria convocatoria) {
        this.convocatoriases.remove(convocatoria);
        convocatoria.setTorneo(null);
        return this;
    }

    public Set<CalendarioEvento> getEventosCalendarios() {
        return this.eventosCalendarios;
    }

    public void setEventosCalendarios(Set<CalendarioEvento> calendarioEventos) {
        if (this.eventosCalendarios != null) {
            this.eventosCalendarios.forEach(i -> i.setTorneo(null));
        }
        if (calendarioEventos != null) {
            calendarioEventos.forEach(i -> i.setTorneo(this));
        }
        this.eventosCalendarios = calendarioEventos;
    }

    public Torneo eventosCalendarios(Set<CalendarioEvento> calendarioEventos) {
        this.setEventosCalendarios(calendarioEventos);
        return this;
    }

    public Torneo addEventosCalendario(CalendarioEvento calendarioEvento) {
        this.eventosCalendarios.add(calendarioEvento);
        calendarioEvento.setTorneo(this);
        return this;
    }

    public Torneo removeEventosCalendario(CalendarioEvento calendarioEvento) {
        this.eventosCalendarios.remove(calendarioEvento);
        calendarioEvento.setTorneo(null);
        return this;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Torneo categoria(Categoria categoria) {
        this.setCategoria(categoria);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Torneo)) {
            return false;
        }
        return getId() != null && getId().equals(((Torneo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Torneo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", reglamento='" + getReglamento() + "'" +
            ", reglamentoContentType='" + getReglamentoContentType() + "'" +
            ", cupoMaximoEquipos=" + getCupoMaximoEquipos() +
            ", cupoMaximoJugadores=" + getCupoMaximoJugadores() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
