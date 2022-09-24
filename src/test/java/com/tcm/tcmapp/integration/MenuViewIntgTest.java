package com.tcm.tcmapp.integration;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.audit.AuditFieldsInterceptorImpl;
import com.tcm.tcmapp.dao.BaseDAO;
import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.entity.*;
import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.view.MenuView;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.spi.TestDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.component.api.Confirmable;
import org.primefaces.model.menu.MenuModel;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Arquillian.class)
public class MenuViewIntgTest {

    @Inject
    MenuView menuView;

    @Inject
    PaginaDAO paginaDAO;

    @Inject
    Pbkdf2PasswordHash passwordHash;
    //--trabajando en corregir los tests para probar el icono almacenado en la clase Pagina vista menuEdit.xhtml

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addPackage(Confirmable.class.getPackage())
                .addPackage(MenuModel.class.getPackage())
                .addClass(MenuView.class)
                .addClass(Pagina.class)
                .addClass(Permiso.class)
                .addClass(Icono.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(PaginasService.class)
                .addClass(PaginaDAO.class)
                .addClass(BaseDAO.class)
                .addClass(IconoDAO.class)
                .addClass(AuditFieldsInterceptor.class)
                .addClass(AuditFieldsInterceptorImpl.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        return war;
    }

    @Before
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
        crearPaginas();
    }

    @After
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
    }

    @Test
    public void pruebaPassworencode() {
        char[] passwordChar = "12345".toCharArray();
        System.out.println("password encoded: " + passwordHash.generate(passwordChar));
        System.out.println("password verification result: " + passwordHash.verify(passwordChar, "PBKDF2WithHmacSHA256:2048:xZBY9KygFVkA66vvfP9jldl9XEPlLQpQkNciQhrDCfc=:j6nPNzxhQJQOCMNcHCxeGI/4Fw7NDxV3H7m0nyysdtg="));
    }

    @Test
    ///@Ignore("Este test presenta errores cuando el bean tiene el scope @ViewScope, pero funciona bien con @SessionScope")
    public void dadoDatosDePagina_cuandoInvocaGetMenuModel_entoncesRetornaContenidoDelMenu() {
        List<String> menuFirstLevelIds = Arrays.asList("1", "8", "12");

        MenuModel menuModel = menuView.getMenuModel();

        assertEquals(3, menuModel.getElements().size());

        assertThat(menuFirstLevelIds)
                .contains(menuModel.getElements().get(0).getId());
        assertTrue(menuFirstLevelIds
                .contains(menuModel.getElements().get(1).getId()));
        assertTrue(menuFirstLevelIds
                .contains(menuModel.getElements().get(2).getId()));
    }

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

    protected void crearPaginas() {
        Pagina primero = new Pagina(1L, "Item 1", "http://item1.com", false, "save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(primero);
        Pagina segundo = new Pagina(2L, "Item 2", "http://item2.com", false, "save", primero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(segundo);
        Pagina tercero = new Pagina(3L, "Item 3", "http://item3.com", false, "save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(tercero);
        Pagina cuarto = new Pagina(4L, "Item 4", "http://item4.com", true, "save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(cuarto);
        Pagina quinto = new Pagina(5L, "Item 5", "http://item5.com", true, "save", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(quinto);
        Pagina sexto = new Pagina(6L, "Item 6", "http://item6.com", false, "save", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(sexto);
        paginaDAO.save(new Pagina(7L, "Item 7", "http://item7.com", true, "save", sexto.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina octavo = new Pagina(8L, "Item 8", "http://item8.com", false, "save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(octavo);
        Pagina noveno = new Pagina(9L, "Item 9", "http://item9.com", false, "save", octavo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(noveno);
        paginaDAO.save(new Pagina(10L, "Item 10", "http://item10.com", true, "save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(11L, "Item 11", "http://item11.com", true, "save", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina doce = new Pagina(12L, "Item 12", "http://item12.com", false, "save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(doce);
        Pagina trece = new Pagina(13L, "Item 13", "http://item13.com", false, "save", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(trece);
        paginaDAO.save(new Pagina(14L, "Item 14", "http://item14.com", true, "save", trece.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(15L, "Item 15", "http://item15.com", true, "save", trece.getId(), LocalDateTime.now(), "mfigueroa", true));
        Pagina dieciseis = new Pagina(16L, "Item 16", "http://item16.com", false, "save", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(dieciseis);
        paginaDAO.save(new Pagina(17L, "Item 17", "http://item17.com", true, "save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(18L, "Item 18", "http://item18.com", true, "save", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));

    }

}