package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.DatosAplicacion;
import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.service.RolesService;
import org.omnifaces.util.Messages;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
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
    DatosAplicacion datosAplicacion;

    @Inject
    RolesService rolesService;

    private DualListModel<Permiso> permisosModel;
    private List<Permiso> permisos;
    //private List<Rol> roles;
    private Rol selectedRol;

    public PermisosRolesView() {
        selectedRol = new Rol();
        permisos = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        permisos = new ArrayList<>(datosAplicacion.getPermisos());

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
        Messages.addInfo(null,"Datos guardados correctamente");
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

}
