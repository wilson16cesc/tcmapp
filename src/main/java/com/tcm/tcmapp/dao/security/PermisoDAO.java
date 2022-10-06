package com.tcm.tcmapp.dao.security;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.security.Permiso;

import javax.ejb.Stateless;

@Stateless
public class PermisoDAO extends BaseDAO<Permiso> {

    public PermisoDAO() {
        super.setEntityClass(Permiso.class);
    }

    public Permiso findByNombre(String nombrePermiso) {
        return findFirstActiveByField("nombre", nombrePermiso);
    }
}
