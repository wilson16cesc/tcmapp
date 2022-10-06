package com.tcm.tcmapp.dao.shared;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.shared.Icono;

import javax.ejb.Stateless;

@Stateless
public class IconoDAO extends BaseDAO<Icono> {

    public IconoDAO() {
        super.setEntityClass(Icono.class);
    }

}
