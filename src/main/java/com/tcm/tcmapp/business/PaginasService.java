package com.tcm.tcmapp.business;

import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.entity.Pagina;

import javax.ejb.Stateless;
import java.util.List;
import javax.inject.Inject;

@Stateless
public class PaginasService {

    @Inject
    PaginaDAO paginaDAO;

    public List<Pagina> getPaginasParaMenu() {
        return paginaDAO.findAllOrderByIdPadre();
    }

}
