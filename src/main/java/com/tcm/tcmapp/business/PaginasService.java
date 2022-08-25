package com.tcm.tcmapp.business;

import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.entity.Pagina;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PaginasService {

    @EJB
    PaginaDAO paginaDAO;

    public List<Pagina> getPaginasParaMenu() {
        return paginaDAO.findAllOrderByIdPadre();
    }

}
