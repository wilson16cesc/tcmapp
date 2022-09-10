package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Permiso;

import javax.ejb.Stateless;

@Stateless
public class PermisoDAO extends BaseDAO<Permiso> {

    public PermisoDAO() {
        super.setEntityClass(Permiso.class);
    }

}
