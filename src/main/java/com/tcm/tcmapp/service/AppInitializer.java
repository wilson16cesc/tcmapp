package com.tcm.tcmapp.service;

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
    }

    private void crearPaginas() {
        logger.info("Ejecutando metodo para cargar paginas.");
        Pagina primero = new Pagina(1L, "Panel de Administracion", "http://item1.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(primero);
        Pagina segundo = new Pagina(2L, "Item 2", "http://item2.com", false, "pi pi-save", primero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(segundo);
        Pagina tercero = new Pagina(3L, "Item 3", "http://item3.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(tercero);
        Pagina cuarto = new Pagina(4L, "Item 4", "http://item4.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(cuarto);
        Pagina quinto = new Pagina(5L, "Item 5", "http://item5.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(quinto);

        Pagina sexto = new Pagina(6L, "Item 6", "http://item6.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(sexto);

        paginaDAO.save(new Pagina(7L, "Item 7", "http://item7.com", true, "pi pi-save", sexto.getId(), LocalDateTime.now(), "mfigueroa", true));
        Pagina octavo = new Pagina(8L, "Reportes Financieros", "http://item8.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(octavo);

        Pagina noveno = new Pagina(9L, "Item 9", "http://item9.com", false, "pi pi-save", octavo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(noveno);

        paginaDAO.save(new Pagina(10L, "Item 10", "http://item10.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(11L, "Item 11", "http://item11.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina doce = new Pagina(12L, "Registros Generales", "http://item12.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(doce);

        Pagina trece = new Pagina(13L, "Item 13", "http://item13.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(trece);

        paginaDAO.save(new Pagina(14L, "Item 14", "http://item14.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(15L, "Item 15", "http://item15.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina dieciseis = new Pagina(16L, "Item 16", "http://item16.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(dieciseis);

        paginaDAO.save(new Pagina(17L, "Item 17", "http://item17.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(18L, "Item 18", "http://item18.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));
    }

}
