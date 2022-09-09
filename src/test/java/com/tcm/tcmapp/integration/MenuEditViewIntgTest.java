package com.tcm.tcmapp.integration;

import com.tcm.tcmapp.bean.DatosAplicacion;
import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.dao.BaseDAO;
import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.dao.PaginaDAO;
import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Icono;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.view.MenuEditView;
import com.tcm.tcmapp.view.MenuInfo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.Confirmable;
import org.primefaces.model.TreeNode;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
public class MenuEditViewIntgTest {

    @Inject
    MenuEditView menuEditView;

    @Inject
    PaginaDAO paginaDAO;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addPackage(TreeNode.class.getPackage())
                .addPackage(Confirmable.class.getPackage())
                .addPackage(PrimeFaces.class.getPackage())
                .addClass(MenuEditView.class)
                .addClass(MenuInfo.class)
                .addClass(Pagina.class)
                .addClass(Icono.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(PaginasService.class)
                .addClass(PaginaDAO.class)
                .addClass(BaseDAO.class)
                .addClass(IconoDAO.class)
                .addClass(MenuCounter.class)
                .addClass(DatosAplicacion.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @BeforeEach
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
        crearPaginas();
    }

    @AfterEach
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
    }

    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuRoot_entoncesDevuelveEstructuraDelMenu() {
        List<String> menuFirstLevelNames = Arrays.asList("Item 1", "Item 8", "Item 12");

        TreeNode<MenuInfo> menuModel = menuEditView.getMenuRoot();

        assertThat(menuModel.getChildren().size()).isEqualTo(3);

        assertTrue(menuFirstLevelNames.contains(menuModel.getChildren().get(0).getData().getName()));
        assertTrue(menuFirstLevelNames.contains(menuModel.getChildren().get(1).getData().getName()));
        assertTrue(menuFirstLevelNames.contains(menuModel.getChildren().get(2).getData().getName()));
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