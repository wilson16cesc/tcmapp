package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Pagina;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

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
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void findMaxId() {
        Pagina pagina = new Pagina(2L, "Item 2", "http://item2.com", false, "save", 1L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(pagina);
        Long maxId = paginaDAO.findMaxId();
        assertEquals(2L, maxId.longValue());
    }

}