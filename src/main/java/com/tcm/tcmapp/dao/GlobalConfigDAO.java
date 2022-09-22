package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.GlobalConfig;

import javax.ejb.Stateless;

@Stateless
public class GlobalConfigDAO extends BaseDAO<GlobalConfig> {

    public GlobalConfigDAO() {
        super.setEntityClass(GlobalConfig.class);
    }

}
