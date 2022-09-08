package com.tcm.tcmapp.integration;

import com.tcm.tcmapp.bean.AppInitializer;
import com.tcm.tcmapp.dao.*;
import com.tcm.tcmapp.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
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

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
//@Ignore///////////////////////////////////////////
public class TransactionsIntgTest {


    @Inject
    PaginaDAO paginaDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    RolDAO rolDAO;

    @Inject
    IconoDAO iconoDAO;

    @Inject
    AppInitializer appInitializer;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(Pagina.class)
                .addClass(Rol.class)
                .addClass(Usuario.class)
                .addClass(Permiso.class)
                .addClass(Icono.class)
                .addClass(BaseDAO.class)
                .addClass(PaginaDAO.class)
                .addClass(UsuarioDAO.class)
                .addClass(RolDAO.class)
                .addClass(IconoDAO.class)
                .addClass(AppInitializer.class)

                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return war;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
        iconoDAO.deleteAll();
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
    }

    @Test
    public void validarAppInitializer() {
        Pagina primeraPagina = paginaDAO.findFirst();
        Usuario primerUsuario = usuarioDAO.findFirst();
        Rol primerRol = rolDAO.findFirst();

        assertNotNull(appInitializer);
        assertNotNull(primeraPagina);
        assertNotNull(primerRol);
        assertNotNull(primerUsuario);
    }


}