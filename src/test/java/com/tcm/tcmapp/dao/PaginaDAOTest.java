package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Icono;
import com.tcm.tcmapp.entity.Pagina;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class PaginaDAOTest {

    @Inject
    PaginaDAO paginaDAO;

    @BeforeEach
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
    }

    @AfterEach
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
    }


    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClass(Pagina.class)
                .addClass(Icono.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(PaginaDAO.class)
                .addClass(BaseDAO.class)
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