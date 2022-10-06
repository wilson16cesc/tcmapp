package com.tcm.tcmapp.service.shared;

import com.tcm.tcmapp.dao.shared.IconoDAO;
import com.tcm.tcmapp.entity.shared.Icono;

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
