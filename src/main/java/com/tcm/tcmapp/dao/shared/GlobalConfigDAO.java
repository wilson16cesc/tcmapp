package com.tcm.tcmapp.dao.shared;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.shared.GlobalConfig;

import javax.ejb.Stateless;

@Stateless
public class GlobalConfigDAO extends BaseDAO<GlobalConfig> {

    public GlobalConfigDAO() {
        super.setEntityClass(GlobalConfig.class);
    }

}
