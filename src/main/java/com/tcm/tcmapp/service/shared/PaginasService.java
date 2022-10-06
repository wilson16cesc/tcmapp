package com.tcm.tcmapp.service.shared;

import com.tcm.tcmapp.dao.shared.PaginaDAO;
import com.tcm.tcmapp.entity.shared.Pagina;

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
