package com.tcm.tcmapp.service;

import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.entity.Icono;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class IconosService {

    @Inject
    IconoDAO iconoDAO;

    public List<Icono> findAllActive(){
        return iconoDAO.findAllActive();
    }
}
