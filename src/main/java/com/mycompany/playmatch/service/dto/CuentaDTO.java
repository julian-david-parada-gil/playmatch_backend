package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Cuenta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuentaDTO implements Serializable {

    private String id;

    private LocalDate fechaNacimiento;

    @NotNull
    @Size(max = 20)
    private String numeroDocumento;

    @NotNull
    @Size(max = 255)
    private String correo;

    @NotNull
    @Size(max = 20)
    private String telefono;

    @Size(max = 200)
    private String direccion;

    private byte[] foto;

    private String fotoContentType;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private UserDTO user;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaDTO)) {
            return false;
        }

        CuentaDTO cuentaDTO = (CuentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuentaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentaDTO{" +
            "id='" + getId() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", estado='" + getEstado() + "'" +
            ", user=" + getUser() +
            ", tipoDocumento=" + getTipoDocumento() +
            "}";
    }
}
