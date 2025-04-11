package com.tcm.tcmapp.service.parametros;

import com.tcm.tcmapp.dao.parametros.DatosDAO;
import com.tcm.tcmapp.entity.parametros.Datos;
import com.tcm.tcmapp.entity.parametros.Listas;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DatosService {

    @Inject
    DatosDAO datosDAO;

    public List<Datos> findAll() {
        return datosDAO.findAll();
    }

    public void update(Datos dato){
        datosDAO.update(dato);
    }

    public Datos findById(Long id) {
        return datosDAO.findById(id);
    }

}
