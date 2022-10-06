package com.tcm.tcmapp.service.security;

import com.tcm.tcmapp.dao.security.PermisoDAO;
import com.tcm.tcmapp.entity.security.Permiso;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PermisosService {

    @Inject
    PermisoDAO permisoDAO;

    public List<Permiso> findAllActive(){
        return permisoDAO.findAllActive();
    }

    public Permiso findById(Long id) {
        return permisoDAO.findById(id);
    }

    public List<Permiso> findAll() {
        return permisoDAO.findAll();
    }

    public void update(Permiso permiso) {
        permisoDAO.update(permiso);
    }
}
