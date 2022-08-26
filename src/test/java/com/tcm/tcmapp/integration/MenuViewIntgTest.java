package com.tcm.tcmapp.integration;

import com.tcm.tcmapp.business.PaginasService;
import com.tcm.tcmapp.dao.BaseDAO;
import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.view.MenuView;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.component.api.Confirmable;
import org.primefaces.model.menu.MenuModel;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@Ignore("Class not ready for tests")
@RunWith(Arquillian.class)
public class MenuViewIntgTest {

    @Inject
    MenuView menuView;

    @Inject
    PaginaDAO paginaDAO;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)

                .addPackages(true, MenuModel.class.getPackage(), Confirmable.class.getPackage())
                .addClasses(MenuView.class, PaginasService.class,
                        Pagina.class, BaseEntityIdentity.class, BaseEntity.class,
                        PaginasService.class, PaginaDAO.class, BaseDAO.class)
                .addAsResource("META-INF/persistence.xml")
                //.addAsWebInfResource("META-INF/glassfish-resources.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
        crearPaginas();
    }

    @Test
    public void givenPaginasData_whenGetMenuModel_thenReturnMenuContent() {
        List<String> menuFirstLevelIds = Arrays.asList("1", "8", "12");

        MenuModel menuModel = menuView.getMenuModel();

        assertEquals(3, menuModel.getElements().size());

        assertTrue(menuFirstLevelIds.contains(menuModel.getElements().get(0).getId()));
        assertTrue(menuFirstLevelIds.contains(menuModel.getElements().get(1).getId()));
        assertTrue(menuFirstLevelIds.contains(menuModel.getElements().get(2).getId()));
    }

    //    @Test
//    public void givenDatabasePagesData_whenGetAll_thenReturnOneRecord(){
//        List result = paginaDAO.findAll();
//        System.out.println(result.get(0));
//        assertEquals(1, result.size());
//    }

    /**
     * con este método se crea la siguiente estructura de menú para el test
     * 0--1--2
     * |      --3
     * |          --4
     * |          --5
     * |       --6
     * |          --7
     * 0--8--9
     * |       --10
     * |       --11
     * 0--12
     * |    --13
     * |        --14
     * |        --15
     * |    --16
     * |        --17
     * |        --18
     */
    private void crearPaginas() {
        Pagina primero = new Pagina("Item 1", "http://item1.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(primero);
        Pagina segundo = new Pagina("Item 2", "http://item2.com", false, "pi pi-save", primero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(segundo);
        Pagina tercero = new Pagina("Item 3", "http://item3.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(tercero);
        Pagina cuarto = new Pagina("Item 4", "http://item4.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(cuarto);
        Pagina quinto = new Pagina("Item 5", "http://item5.com", true, "pi pi-save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(quinto);
        Pagina sexto = new Pagina("Item 6", "http://item6.com", false, "pi pi-save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(sexto);
        paginaDAO.save(new Pagina("Item 7", "http://item7.com", true, "pi pi-save", sexto.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

        Pagina octavo = new Pagina("Item 8", "http://item8.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(octavo);
        Pagina noveno = new Pagina("Item 9", "http://item9.com", false, "pi pi-save", octavo.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(noveno);
        paginaDAO.save(new Pagina("Item 10", "http://item10.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 11", "http://item11.com", true, "pi pi-save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

        Pagina doce = new Pagina("Item 12", "http://item12.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(doce);
        Pagina trece = new Pagina("Item 13", "http://item13.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(trece);
        paginaDAO.save(new Pagina("Item 14", "http://item14.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 15", "http://item15.com", true, "pi pi-save", trece.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        Pagina dieciseis = new Pagina("Item 16", "http://item16.com", false, "pi pi-save", doce.getId(), LocalDateTime.now(), "mfigueroa", true, 0);
        paginaDAO.save(dieciseis);
        paginaDAO.save(new Pagina("Item 17", "http://item17.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));
        paginaDAO.save(new Pagina("Item 18", "http://item18.com", true, "pi pi-save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true, 0));

    }

    @After
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
    }
}