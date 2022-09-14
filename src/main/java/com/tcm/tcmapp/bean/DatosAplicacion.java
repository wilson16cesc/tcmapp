package com.tcm.tcmapp.bean;


import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.dao.PermisoDAO;
import com.tcm.tcmapp.dao.RolDAO;
import com.tcm.tcmapp.entity.Icono;
import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.entity.Rol;
import org.omnifaces.cdi.Eager;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Eager
@Named
public class DatosAplicacion {

    @Inject
    IconoDAO iconoDAO;

    @Inject
    RolDAO rolDAO;

    @Inject
    PermisoDAO permisoDAO;


    private List<Icono> iconos;
    private List<Rol> roles;
    private List<Permiso> permisos;

    @PostConstruct
    public void initApplicationData() {
        iconos = iconoDAO.findAllActive();
        roles = rolDAO.findAllActiveWithPermissions();
        permisos = permisoDAO.findAllActive();

    }

    public List<Icono> getIconos() {
        return iconos;
    }

    public void setIconos(List<Icono> iconos) {
        this.iconos = iconos;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

}
