package com.tcm.tcmapp.service;

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
        List<Pagina> paginas = paginaDAO.findAllOrderByIdPadre();
        paginas.forEach(pagina -> pagina.getPermisos());
        return paginas;
    }

    public void saveAll(List<Pagina> paginas) {
        paginas.forEach(pagina -> {
            paginaDAO.save(pagina);
        });
    }
    public void saveOrUpdateAll(List<Pagina> paginas) {
        paginas.forEach(pagina -> {
            pagina.getPermisos();
            paginaDAO.update(pagina);
        });
    }
}
