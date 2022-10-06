package com.tcm.tcmapp.dao.security;

import com.tcm.tcmapp.dao.base.BaseReadOnlyDAO;
import com.tcm.tcmapp.entity.security.VUsuarioRol;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class VUsuarioRolDAO extends BaseReadOnlyDAO<VUsuarioRol> {

    public VUsuarioRolDAO() {
        super.setEntityClass(VUsuarioRol.class);
    }

    public List<VUsuarioRol> findByUsername(String username) {
        return findByField("username", username);
    }
}
