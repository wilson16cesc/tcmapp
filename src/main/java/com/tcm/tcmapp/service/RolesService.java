package com.tcm.tcmapp.service;

import com.tcm.tcmapp.dao.RolDAO;
import com.tcm.tcmapp.entity.Rol;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RolesService {

    @Inject
    RolDAO rolDAO;


    public void update(Rol rol) {
        rolDAO.update(rol);
    }

    public List<Rol> findAllActiveWithPermisos() {
        return rolDAO.findAllActiveWithPermissions();
    }

    public void save(Rol rolEditar) {
        rolDAO.save(rolEditar);
    }
    
}
