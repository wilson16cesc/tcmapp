package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Pagina;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class PaginaDAO extends BaseDAO<Pagina> {

    public PaginaDAO() {
        super.setEntityClass(Pagina.class);
    }

    public List<Pagina> findAllOrderByIdPadre() {
        return getEntityManager().createNamedQuery("Pagina.findAllOrderByIdPadre", Pagina.class)
                .getResultList();
    }

    public Long findMaxId() {
        return getEntityManager().createNamedQuery("Pagina.findMaxId", Long.class)
                .getSingleResult();
    }
}
