package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.DatosAplicacion;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.entity.Rol;
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
    DatosAplicacion datosAplicacion;

    private DualListModel<Permiso> permisosModel;
    private List<Permiso> permisosOrigen = new ArrayList<>();
    private List<Permiso> permisosDestino = new ArrayList<>();

    private List<Rol> roles;
    private Rol selectedRol;

    public PermisosRolesView() {

    }

    @PostConstruct
    public void init() {
        permisosOrigen = datosAplicacion.getPermisos();

        permisosModel = new DualListModel<>(
                permisosOrigen,
                permisosDestino
        );
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Rol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(Rol selectedRol) {
        this.selectedRol = selectedRol;
    }

    public DualListModel<? extends BaseEntityIdentity> getPermisosModel() {
        return permisosModel;
    }

    public void setPermisosModel(DualListModel<Permiso> permisosModel) {
        this.permisosModel = permisosModel;
    }

    public List<Permiso> getPermisosOrigen() {
        return permisosOrigen;
    }

    public void setPermisosOrigen(List<Permiso> permisosOrigen) {
        this.permisosOrigen = permisosOrigen;
    }

    public List<Permiso> getPermisosDestino() {
        return permisosDestino;
    }

    public void setPermisosDestino(List<Permiso> permisosDestino) {
        this.permisosDestino = permisosDestino;
    }

    public void transfer() {
        logger.info("origen: {} - destino: {}",
                Arrays.toString(permisosModel.getSource().toArray()), Arrays.toString(permisosModel.getTarget().toArray()));

    }
}
