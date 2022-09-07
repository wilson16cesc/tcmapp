package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Rol;
import javax.ejb.Stateless;


@Stateless
public class RolDAO extends BaseDAO<Rol>{
    public RolDAO() {
        super.setEntityClass(Rol.class);
    }


}
