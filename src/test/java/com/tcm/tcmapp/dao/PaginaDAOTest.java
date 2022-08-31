package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.view.MenuEditView;
import com.tcm.tcmapp.view.MenuInfo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.Confirmable;
import org.primefaces.model.TreeNode;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
@RunWith(Arquillian.class)
public class PaginaDAOTest {

    @Inject
    PaginaDAO paginaDAO;

    @Before
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
    }


    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClasses(Pagina.class, BaseEntityIdentity.class, BaseEntity.class,
                         PaginaDAO.class, BaseDAO.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void findMaxId() {
        Pagina pagina = new Pagina(2L, "Item 2", "http://item2.com", false, "pi pi-save", 1L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(pagina);
        Long maxId = paginaDAO.findMaxId();
        assertEquals(2L, maxId.longValue());
    }

}