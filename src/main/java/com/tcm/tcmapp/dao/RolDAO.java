package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Rol;

import javax.ejb.Stateless;
import java.util.List;


@Stateless
public class RolDAO extends BaseDAO<Rol> {

    public static final String NOMBRE = "nombre";

    public RolDAO() {
        super.setEntityClass(Rol.class);
    }

    public Rol findFirstByNombre(String nombre) {
        return findFirstActiveByField(NOMBRE, nombre);
    }

}
