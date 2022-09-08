package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Icono;

import javax.ejb.Stateless;

@Stateless
public class IconoDAO extends BaseDAO<Icono> {

    public IconoDAO() {
        super.setEntityClass(Icono.class);
    }

}
