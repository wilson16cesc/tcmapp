package com.tcm.tcmapp.business;

import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Pagina;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Singleton
@Startup
public class AppInitializer {

    final Logger logger = LoggerFactory.getLogger(AppInitializer.class.getSimpleName());
    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    PaginaDAO paginaDAO;

    @PostConstruct
    private void initializeApp() {
        logger.info("Borrando datos del menu en base de datos.");
        paginaDAO.deleteAll();
        crearPaginas();
        logger.info("Menu cardado desde base de datos.");
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
        logger.info("Ejecutando metodo para cargar paginas.");
        Pagina primero = new Pagina("Panel de Administracion", "http://item1.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(primero);
        paginaDAO.flush();
        Pagina segundo = new Pagina("Item 2", "http://item2.com", false, "pi pi-save", primero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(segundo);
        paginaDAO.flush();
        Pagina tercero = new Pagina("Item 3", "http://item3.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(tercero);
        paginaDAO.flush();
        Pagina cuarto = new Pagina("Item 4", "http://item4.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(cuarto);
        paginaDAO.flush();
        Pagina quinto = new Pagina("Item 5", "http://item5.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(quinto);
        paginaDAO.flush();
        Pagina sexto = new Pagina("Item 6", "http://item6.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(sexto);
        paginaDAO.flush();
        paginaDAO.save(new Pagina("Item 7", "http://item7.com", true, "pi pi-save", sexto.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        Pagina octavo = new Pagina("Reportes Financieros", "http://item8.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(octavo);
        paginaDAO.flush();
        Pagina noveno = new Pagina("Item 9", "http://item9.com", false, "pi pi-save", octavo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(noveno);
        paginaDAO.flush();
        paginaDAO.save(new Pagina("Item 10", "http://item10.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 11", "http://item11.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

        Pagina doce = new Pagina("Registros Generales", "http://item12.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(doce);
        paginaDAO.flush();
        Pagina trece = new Pagina("Item 13", "http://item13.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(trece);
        paginaDAO.flush();
        paginaDAO.save(new Pagina("Item 14", "http://item14.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 15", "http://item15.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        
        Pagina dieciseis = new Pagina("Item 16", "http://item16.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(dieciseis);
        paginaDAO.flush();
        paginaDAO.save(new Pagina("Item 17", "http://item17.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 18", "http://item18.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
    }

}
