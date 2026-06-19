package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Cuenta.
 */
@Document(collection = "cuenta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cuenta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull
    @Size(max = 20)
    @Field("numero_documento")
    private String numeroDocumento;

    @NotNull
    @Size(max = 255)
    @Field("correo")
    private String correo;

    @NotNull
    @Size(max = 20)
    @Field("telefono")
    private String telefono;

    @Size(max = 200)
    @Field("direccion")
    private String direccion;

    @Field("foto")
    private byte[] foto;

    @Field("foto_content_type")
    private String fotoContentType;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("grupos")
    @JsonIgnoreProperties(value = { "grupo", "usuario" }, allowSetters = true)
    private Set<MiembroGrupo> gruposes = new HashSet<>();

    @DBRef
    @Field("inscripciones")
    @JsonIgnoreProperties(value = { "torneo", "usuario" }, allowSetters = true)
    private Set<Inscripcion> inscripcioneses = new HashSet<>();

    @DBRef
    @Field("mensajes")
    @JsonIgnoreProperties(value = { "grupo", "autor" }, allowSetters = true)
    private Set<MensajeGrupo> mensajeses = new HashSet<>();

    @DBRef
    @Field("calificaciones")
    @JsonIgnoreProperties(value = { "torneo", "autor" }, allowSetters = true)
    private Set<Calificacion> calificacioneses = new HashSet<>();

    @DBRef
    @Field("noticias")
    @JsonIgnoreProperties(value = { "autor" }, allowSetters = true)
    private Set<Noticia> noticiases = new HashSet<>();

    @DBRef
    @Field("notificaciones")
    @JsonIgnoreProperties(value = { "destinatario" }, allowSetters = true)
    private Set<Notificacion> notificacioneses = new HashSet<>();

    @DBRef
    @Field("tipoDocumento")
    @JsonIgnoreProperties(value = { "cuentases" }, allowSetters = true)
    private TipoDocumento tipoDocumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Cuenta id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Cuenta fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public Cuenta numeroDocumento(String numeroDocumento) {
        this.setNumeroDocumento(numeroDocumento);
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Cuenta correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Cuenta telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Cuenta direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public Cuenta foto(byte[] foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return this.fotoContentType;
    }

    public Cuenta fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Cuenta estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cuenta user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<MiembroGrupo> getGruposes() {
        return this.gruposes;
    }

    public void setGruposes(Set<MiembroGrupo> miembroGrupos) {
        if (this.gruposes != null) {
            this.gruposes.forEach(i -> i.setUsuario(null));
        }
        if (miembroGrupos != null) {
            miembroGrupos.forEach(i -> i.setUsuario(this));
        }
        this.gruposes = miembroGrupos;
    }

    public Cuenta gruposes(Set<MiembroGrupo> miembroGrupos) {
        this.setGruposes(miembroGrupos);
        return this;
    }

    public Cuenta addGrupos(MiembroGrupo miembroGrupo) {
        this.gruposes.add(miembroGrupo);
        miembroGrupo.setUsuario(this);
        return this;
    }

    public Cuenta removeGrupos(MiembroGrupo miembroGrupo) {
        this.gruposes.remove(miembroGrupo);
        miembroGrupo.setUsuario(null);
        return this;
    }

    public Set<Inscripcion> getInscripcioneses() {
        return this.inscripcioneses;
    }

    public void setInscripcioneses(Set<Inscripcion> inscripcions) {
        if (this.inscripcioneses != null) {
            this.inscripcioneses.forEach(i -> i.setUsuario(null));
        }
        if (inscripcions != null) {
            inscripcions.forEach(i -> i.setUsuario(this));
        }
        this.inscripcioneses = inscripcions;
    }

    public Cuenta inscripcioneses(Set<Inscripcion> inscripcions) {
        this.setInscripcioneses(inscripcions);
        return this;
    }

    public Cuenta addInscripciones(Inscripcion inscripcion) {
        this.inscripcioneses.add(inscripcion);
        inscripcion.setUsuario(this);
        return this;
    }

    public Cuenta removeInscripciones(Inscripcion inscripcion) {
        this.inscripcioneses.remove(inscripcion);
        inscripcion.setUsuario(null);
        return this;
    }

    public Set<MensajeGrupo> getMensajeses() {
        return this.mensajeses;
    }

    public void setMensajeses(Set<MensajeGrupo> mensajeGrupos) {
        if (this.mensajeses != null) {
            this.mensajeses.forEach(i -> i.setAutor(null));
        }
        if (mensajeGrupos != null) {
            mensajeGrupos.forEach(i -> i.setAutor(this));
        }
        this.mensajeses = mensajeGrupos;
    }

    public Cuenta mensajeses(Set<MensajeGrupo> mensajeGrupos) {
        this.setMensajeses(mensajeGrupos);
        return this;
    }

    public Cuenta addMensajes(MensajeGrupo mensajeGrupo) {
        this.mensajeses.add(mensajeGrupo);
        mensajeGrupo.setAutor(this);
        return this;
    }

    public Cuenta removeMensajes(MensajeGrupo mensajeGrupo) {
        this.mensajeses.remove(mensajeGrupo);
        mensajeGrupo.setAutor(null);
        return this;
    }

    public Set<Calificacion> getCalificacioneses() {
        return this.calificacioneses;
    }

    public void setCalificacioneses(Set<Calificacion> calificacions) {
        if (this.calificacioneses != null) {
            this.calificacioneses.forEach(i -> i.setAutor(null));
        }
        if (calificacions != null) {
            calificacions.forEach(i -> i.setAutor(this));
        }
        this.calificacioneses = calificacions;
    }

    public Cuenta calificacioneses(Set<Calificacion> calificacions) {
        this.setCalificacioneses(calificacions);
        return this;
    }

    public Cuenta addCalificaciones(Calificacion calificacion) {
        this.calificacioneses.add(calificacion);
        calificacion.setAutor(this);
        return this;
    }

    public Cuenta removeCalificaciones(Calificacion calificacion) {
        this.calificacioneses.remove(calificacion);
        calificacion.setAutor(null);
        return this;
    }

    public Set<Noticia> getNoticiases() {
        return this.noticiases;
    }

    public void setNoticiases(Set<Noticia> noticias) {
        if (this.noticiases != null) {
            this.noticiases.forEach(i -> i.setAutor(null));
        }
        if (noticias != null) {
            noticias.forEach(i -> i.setAutor(this));
        }
        this.noticiases = noticias;
    }

    public Cuenta noticiases(Set<Noticia> noticias) {
        this.setNoticiases(noticias);
        return this;
    }

    public Cuenta addNoticias(Noticia noticia) {
        this.noticiases.add(noticia);
        noticia.setAutor(this);
        return this;
    }

    public Cuenta removeNoticias(Noticia noticia) {
        this.noticiases.remove(noticia);
        noticia.setAutor(null);
        return this;
    }

    public Set<Notificacion> getNotificacioneses() {
        return this.notificacioneses;
    }

    public void setNotificacioneses(Set<Notificacion> notificacions) {
        if (this.notificacioneses != null) {
            this.notificacioneses.forEach(i -> i.setDestinatario(null));
        }
        if (notificacions != null) {
            notificacions.forEach(i -> i.setDestinatario(this));
        }
        this.notificacioneses = notificacions;
    }

    public Cuenta notificacioneses(Set<Notificacion> notificacions) {
        this.setNotificacioneses(notificacions);
        return this;
    }

    public Cuenta addNotificaciones(Notificacion notificacion) {
        this.notificacioneses.add(notificacion);
        notificacion.setDestinatario(this);
        return this;
    }

    public Cuenta removeNotificaciones(Notificacion notificacion) {
        this.notificacioneses.remove(notificacion);
        notificacion.setDestinatario(null);
        return this;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Cuenta tipoDocumento(TipoDocumento tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        return getId() != null && getId().equals(((Cuenta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
