package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.*;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class RolDAOTest extends BaseDAO {


    @Inject
    RolDAO rolDAO;

    @BeforeEach
    public void setUp() throws Exception {
        Rol rol1 = new Rol("user_role");
        rolDAO.save(rol1);
    }

    @AfterEach
    public void tearDown() throws Exception {
        rolDAO.deleteAll();
    }

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClass(Usuario.class)
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(UsuarioDAO.class)
                .addClass(RolDAO.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }


    @Test
    public void testFindAllActive() {
        List<Rol> rolesActivos = rolDAO.findAllActive();
        assertEquals(1, rolesActivos.size());
    }
}
