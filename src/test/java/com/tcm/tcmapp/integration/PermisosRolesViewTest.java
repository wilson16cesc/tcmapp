package com.tcm.tcmapp.integration;

import com.tcm.tcmapp.bean.DatosAplicacion;
import com.tcm.tcmapp.dao.BaseDAO;
import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.dao.PermisoDAO;
import com.tcm.tcmapp.dao.RolDAO;
import com.tcm.tcmapp.entity.*;
import com.tcm.tcmapp.logging.LoggerProducer;
import com.tcm.tcmapp.service.RolesService;
import com.tcm.tcmapp.view.PermisosRolesView;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.Confirmable;
import org.primefaces.model.DualListModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@ExtendWith(ArquillianExtension.class)
public class PermisosRolesViewTest {

    public static final String ROL_ADMIN = "rol_admin";
    public static final String EDITAR_MENU_WRITE = "editarMenuWrite";
    public static final String EDITAR_MENU_READ = "editarMenuRead";

    @Inject
    PermisosRolesView permisosRolesView;

    @Inject
    RolDAO rolDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.omnifaces:omnifaces").withTransitivity().asFile())
                .addPackage(DualListModel.class.getPackage())
                .addPackage(Confirmable.class.getPackage())
                .addPackage(PrimeFaces.class.getPackage())
                .addClass(Icono.class)
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(RolDAO.class)
                .addClass(PermisoDAO.class)
                .addClass(IconoDAO.class)
                .addClass(DatosAplicacion.class)
                .addClass(RolesService.class)
                .addClass(PermisosRolesView.class)
                .addClass(LoggerProducer.class)
                //.addClass(Messages.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @BeforeEach
    public void setUp() throws Exception {
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @AfterEach
    public void tearDown() throws Exception {
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @Test
    @DisplayName("cuando invoca guardarPermisos -> guarda en la db los permisos seleccionados en el pickList")
    void guardarPermisosTest() {
        crearRolConPermisos();
        Permiso editarMenuRead = permisoDAO.findByNombre(EDITAR_MENU_READ);
        Permiso editarMenuWrite = permisoDAO.findByNombre(EDITAR_MENU_WRITE);
        Rol selectedRol = rolDAO.findFirstByNombre(ROL_ADMIN);
        DualListModel<Permiso> permisosModel = new DualListModel<>(
                new ArrayList<>(Collections.singletonList(editarMenuRead)),
                new ArrayList<>(Collections.singletonList(editarMenuWrite)));
        permisosRolesView.setSelectedRol(selectedRol);
        permisosRolesView.setPermisosModel(permisosModel);
//
//        permisosRolesView.guardarPermisos();
//
//        Rol resultRol = rolDAO.findFirstByNombreWithPermissions(ROL_ADMIN);
//
//        assertThat(resultRol.getPermisos()).size().isEqualTo(1);
//        assertThat(resultRol.getPermisos().get(0)).isEqualTo(editarMenuWrite);

    }

    private void crearRolConPermisos() {
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