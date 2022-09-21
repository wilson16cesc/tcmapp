package com.tcm.tcmapp.service;

import com.tcm.tcmapp.dao.PermisoDAO;
import com.tcm.tcmapp.entity.Permiso;

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
