package com.tcm.tcmapp.bean;

import com.tcm.tcmapp.dao.shared.PaginaDAO;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author Miguel_Figueroa2
 */
@Singleton
public class MenuCounter {

    @Inject
    PaginaDAO paginaDAO;
    
    private Long lastId;

    @PostConstruct
    public void init() {
        lastId = paginaDAO.findMaxId();
    }

    public Long getNextId() {
        return ++lastId;
    }
    
    

}
