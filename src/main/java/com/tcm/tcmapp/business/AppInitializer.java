package com.tcm.tcmapp.business;

import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.Pagina;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class AppInitializer {

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    PaginaDAO paginaDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @PostConstruct
    private void initializeApp() {
        System.out.println("Creando Páginas de Menú....");
        paginaDAO.deleteAll();
        
       
        crearPaginas();
        System.out.println("Paginas creadas.....");
        /*
        usuarioDAO.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUsername("mfigueroa");
        usuario.setPassword("password");
        usuario.setFechaCrea(LocalDateTime.now());
        usuario.setUsuarioCrea("mfigueroa");
        usuario.setActivo(true);

        usuarioDAO.save(usuario);
         */
    }


    private void crearPaginas() {
        Pagina primero = new Pagina(1L, "Item 1", "http://item1.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(primero);
        Pagina segundo = new Pagina(2L, "Item 2", "http://item2.com", false, "pi pi-save", primero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(segundo);
        Pagina tercero = new Pagina(3L, "Item 3", "http://item3.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        Pagina cuarto = new Pagina(4L, "Item 4", "http://item4.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(cuarto);
        Pagina quinto = new Pagina(5L, "Item 5", "http://item5.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(quinto);
        Pagina sexto = new Pagina(6L, "Item 6", "http://item6.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(sexto);
        paginaDAO.save(new Pagina(7L, "Item 7", "http://item7.com", true, "pi pi-save", sexto.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

        Pagina octavo = new Pagina(8L, "Item 8", "http://item8.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(octavo);
        Pagina noveno = new Pagina(9L, "Item 9", "http://item9.com", false, "pi pi-save", octavo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(noveno);
        paginaDAO.save(new Pagina(10L, "Item 10", "http://item10.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina(11L, "Item 11", "http://item11.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

        Pagina doce = new Pagina(12L, "Item 12", "http://item12.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(doce);
        Pagina trece = new Pagina(13L, "Item 13", "http://item13.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(trece);
        paginaDAO.save(new Pagina(14L, "Item 14", "http://item14.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina(15L, "Item 15", "http://item15.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        Pagina dieciseis = new Pagina(16L, "Item 16", "http://item16.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(dieciseis);
        paginaDAO.save(new Pagina(17L, "Item 17", "http://item17.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina(18L, "Item 18", "http://item18.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
    }

    public void crearPagina(BaseEntity pagina) {
        try {
            userTransaction.begin();
            entityManager.persist(pagina);
            entityManager.flush();
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    userTransaction.rollback();
                }
            } catch (Throwable e) {
                // ignore
            }
        }
    }
        public void eliminarPaginas() {
        try {
            userTransaction.begin();
            entityManager.createQuery("DELETE from Pagina p");
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    userTransaction.rollback();
                }
            } catch (Throwable e) {
                // ignore
            }
        }
    }

}
