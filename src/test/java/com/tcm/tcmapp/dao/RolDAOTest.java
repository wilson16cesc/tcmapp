package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.*;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class RolDAOTest extends BaseDAO {


    @Inject
    RolDAO rolDAO;

    @Before
    public void setUp() throws Exception {
        Rol rol1 = new Rol("user_role");
        rolDAO.save(rol1);
    }



    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addClasses(Usuario.class, Rol.class, Permiso.class, BaseEntityIdentity.class, BaseEntity.class,
                        UsuarioDAO.class, RolDAO.class, BaseDAO.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void testFindAllActive() {
        List<Rol> rolesActivos = rolDAO.findAllActive();
        assertEquals(1, rolesActivos.size());
    }

    @After
    public void tearDown() throws Exception {
        rolDAO.deleteAll();
    }
}