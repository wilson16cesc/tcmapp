package com.tcm.tcmapp.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolPaginaId implements Serializable {
    private static final long serialVersionUID = 2988277745737618305L;

    @Column(name = "rol_id", nullable = false)
    private Long rolId;

    @Column(name = "pagina_id", nullable = false)
    private Long pagina_id;

    public Long getPagina_id() {
        return pagina_id;
    }

    public void setPagina_id(Long pagina_id) {
        this.pagina_id = pagina_id;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolPaginaId entity = (RolPaginaId) o;
        return Objects.equals(this.rolId, entity.rolId) &&
                Objects.equals(this.pagina_id, entity.pagina_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolId, pagina_id);
    }
}