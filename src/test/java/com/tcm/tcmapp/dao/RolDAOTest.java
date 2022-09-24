package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.audit.AuditFieldsInterceptorImpl;
import com.tcm.tcmapp.entity.*;
import com.tcm.tcmapp.logging.LoggerProducer;
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
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class RolDAOTest {

    public static final String ROL_ADMIN = "ADMIN";
    public static final String EDITAR_MENU_READ = "EditarMenuRead";
    public static final String EDITAR_MENU_WRITE = "EditarMenuWrite";

    @Inject
    RolDAO rolDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Inject
    Logger logger;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(Pagina.class)
                .addClass(Icono.class)
                .addClass(Usuario.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(UsuarioDAO.class)
                .addClass(IconoDAO.class)
                .addClass(RolDAO.class)
                .addClass(PermisoDAO.class)
                .addClass(LoggerProducer.class)
                .addClass(AuditFieldsInterceptor.class)
                .addClass(AuditFieldsInterceptorImpl.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        //System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void setUp() throws Exception {
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @Test
    public void findFirstByName() {
        Rol adminRol = new Rol(ROL_ADMIN);
        rolDAO.save(adminRol);

        Rol resultRol = rolDAO.findFirstByNombre(ROL_ADMIN);

        assertThat(resultRol).isNotNull();

    }

    @Test
    public void testFindAllActive() {
        Rol adminRol = new Rol("USER");
        rolDAO.save(adminRol);

        List<Rol> rolesActivos = rolDAO.findAllActive();
        assertThat(rolesActivos).size().isEqualTo(1);
    }

    @Test
    public void cuandoRemuevePermisosDelObjetoRol_entoncesActualizaPermisosAlActualizarElRol() {
        crearRolConPermisos();
        Permiso editarMenuRead = permisoDAO.findByNombre(EDITAR_MENU_READ);
        Rol rolAdmin = rolDAO.findFirstByNombreWithPermissions(ROL_ADMIN);

        assertThat(rolAdmin.getPermisos()).size().isEqualTo(2);

        rolAdmin.getPermisos().remove(editarMenuRead);
        rolDAO.update(rolAdmin);

        Rol rolAdmin2 = rolDAO.findFirstByNombreWithPermissions(ROL_ADMIN);

        assertThat(rolAdmin2.getPermisos()).size().isEqualTo(1);
    }

    private void crearRolConPermisos() {
        logger.info("Creando roles usuarios y permisos en la base de datos");
        Rol rol1Admin = new Rol(ROL_ADMIN);
        rolDAO.save(rol1Admin);

        Permiso editarMenuRead = new Permiso(EDITAR_MENU_READ, "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso(EDITAR_MENU_WRITE, "Tiene permiso de escritura en la ventana 'Editar Menu");

        permisoDAO.save(editarMenuRead);
        permisoDAO.save(editarMenuWrite);

        rol1Admin.setPermisos(Arrays.asList(editarMenuRead, editarMenuWrite));
        rolDAO.update(rol1Admin);
    }

}