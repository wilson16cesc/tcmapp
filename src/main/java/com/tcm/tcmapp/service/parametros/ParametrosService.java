package com.tcm.tcmapp.service.parametros;


import com.tcm.tcmapp.dao.parametros.ParametrosDAO;
import com.tcm.tcmapp.entity.parametros.Parametros;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ParametrosService {

    @Inject
    ParametrosDAO parametrosDAO;

    public List<Parametros> findAll() {
        return parametrosDAO.findAll();
    }

    public void update(Parametros parametro){
        parametrosDAO.update(parametro);
    }

    public Parametros findById(Long id) {
        return parametrosDAO.findById(id);
    }



}
