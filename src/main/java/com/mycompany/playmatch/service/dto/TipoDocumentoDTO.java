package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.TipoDocumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDocumentoDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 20)
    private String sigla;

    @NotNull
    @Size(max = 100)
    private String nombreDocumento;

    @NotNull
    private EstadoGeneral estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumentoDTO)) {
            return false;
        }

        TipoDocumentoDTO tipoDocumentoDTO = (TipoDocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDocumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumentoDTO{" +
            "id='" + getId() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", nombreDocumento='" + getNombreDocumento() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
