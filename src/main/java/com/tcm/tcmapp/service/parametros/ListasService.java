package com.tcm.tcmapp.service.parametros;


import com.tcm.tcmapp.dao.parametros.ListasDAO;
import com.tcm.tcmapp.entity.parametros.Listas;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ListasService {

    @Inject
    ListasDAO listasDAO;


    public List<Listas> findAll() {
        return listasDAO.findAll();
    }

    public void update(Listas lista){
        listasDAO.update(lista);
    }

    public void save(Listas lista){
        listasDAO.save(lista);
    }

    public Listas findById(Long id) {
        return listasDAO.findById(id);
    }

}
