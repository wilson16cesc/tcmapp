package com.tcm.tcmapp.view.security;

import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.entity.security.Rol;
import com.tcm.tcmapp.service.security.PermisosService;
import com.tcm.tcmapp.service.security.RolesService;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class PermisosRolesView implements Serializable {

    @Inject
    Logger logger;

    @Inject
    RolesService rolesService;

    @Inject
    PermisosService permisosService;

    private DualListModel<Permiso> permisosModel;
    private List<Permiso> permisos;

    private Rol selectedRol;
    private Rol rolEditar;
    private List<Rol> roles;

    public PermisosRolesView() {
        selectedRol = new Rol();
        permisos = new ArrayList<>();
        roles = new ArrayList<>();
        rolEditar = new Rol();
    }

    @PostConstruct
    public void init() {
        permisos = new ArrayList<>(permisosService.findAllActive());
        roles = rolesService.findAllActiveWithPermisos();

        permisosModel = new DualListModel<>(
                new ArrayList<>(permisos),
                new ArrayList<>());
    }

    public Rol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(Rol selectedRol) {
        this.selectedRol = selectedRol;
    }

    public DualListModel<Permiso> getPermisosModel() {
        return permisosModel;
    }

    public void setPermisosModel(DualListModel<Permiso> permisosModel) {
        this.permisosModel = permisosModel;
    }

    public void transfer() {
        logger.info("origen: {} - destino: {}",
                Arrays.toString(permisosModel.getSource().toArray()), Arrays.toString(permisosModel.getTarget().toArray()));
    }

    public void cargarDatosRol() {
        logger.info("Permisos del rol: {} - {}",
                selectedRol.getNombre(), Arrays.toString(selectedRol.getPermisos().toArray()));

        ArrayList<Permiso> permisosOrigen = new ArrayList<>(permisos);
        permisosOrigen.removeAll(selectedRol.getPermisos());
        permisosModel = new DualListModel<>(
                permisosOrigen,
                new ArrayList<>(selectedRol.getPermisos())
        );
    }

    public void guardarPermisos() {
        List<Permiso> selectedPermisos = permisosModel.getTarget();
        selectedRol.setPermisos(selectedPermisos);
        rolesService.update(selectedRol);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public void guardarRol() {
        rolesService.save(rolEditar);
        selectedRol = rolEditar;
        roles.add(rolEditar);
        cargarDatosRol();
        PrimeFaces.current().executeScript("PF('dlgRol').hide();");
    }
    
    public void crearEditarRol(){
        rolEditar = new Rol();
        PrimeFaces.current().executeScript("PF('dlgRol').show();");
    }
    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Rol getRolEditar() {
        return rolEditar;
    }

    public void setRolEditar(Rol rolEditar) {
        this.rolEditar = rolEditar;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
