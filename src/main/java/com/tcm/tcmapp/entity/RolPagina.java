package com.tcm.tcmapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "seg_roles_paginas")
public class RolPagina extends BaseEntity{
    @EmbeddedId
    private RolPaginaId rolPaginaId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "pagina_id", nullable = false)
    private Pagina pagina;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "acceso_id", nullable = false)
    private Acceso acceso;

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public RolPaginaId getRolPaginaId() {
        return rolPaginaId;
    }

    public void setRolPaginaId(RolPaginaId rolPaginaId) {
        this.rolPaginaId = rolPaginaId;
    }


}