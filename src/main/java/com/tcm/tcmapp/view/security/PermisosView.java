/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tcm.tcmapp.view.security;

import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.service.security.PermisosService;
import com.tcm.tcmapp.service.security.RolesService;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PermisosView implements Serializable {

    @Inject
    PermisosService permisosService;

    @Inject
    Logger logger;

    @Inject
    RolesService rolesService;

    private List<Permiso> permisos;
    private Permiso selectedPermiso;

    public PermisosView() {
        permisos = new ArrayList<>();
        selectedPermiso = new Permiso();
    }

    @PostConstruct
    private void init() {
        String accion = String.valueOf(Faces.getRequestParameter("accion"));
        logger.info("accion::: {}", accion);
        switch (accion) {
            case "crear":
                selectedPermiso = new Permiso();
                break;
            case "editar":
                String permisoId = Faces.getRequestParameter("permisoId");
                selectedPermiso = permisosService.findById(Long.valueOf(permisoId));
                break;
            default:
                permisos = permisosService.findAll();
        }
        logger.info("Permiso: {}", selectedPermiso);
    }

    public void guardarPermiso(){
        permisosService.update(selectedPermiso);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Permiso getSelectedPermiso() {
        return selectedPermiso;
    }

    public void setSelectedPermiso(Permiso selectedPermiso) {
        this.selectedPermiso = selectedPermiso;
    }

    public void nuevoPermiso(){
        logger.info("ejecutando metodo: nuevoPermiso");
        selectedPermiso = new Permiso();
    }

    
}
