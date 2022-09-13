package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
@Disabled
class VUsuarioRolDAOTest {

    public static final String ADMIN1 = "admin1";
    public static final String USUARIO1 = "usuario1";
    @Inject
    Logger logger;

    @Inject
    RolDAO rolDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Inject
    VUsuarioRolDAO vUsuarioRolDAO;


    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addClass(Usuario.class)
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(VUsuarioRol.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(UsuarioDAO.class)
                .addClass(RolDAO.class)
                .addClass(VUsuarioRolDAO.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @BeforeEach
    void setUp() {
        System.out.println("Preparando test...");
        //crear vista en db h2
        String sql = "CREATE VIEW IF NOT EXISTS vseg_usuarios_roles AS select random_uuid() as id, username, rolename from\n" +
                "            (select u.username, r.nombre as rolename from seg_usuarios u\n" +
                "            inner join seg_usuarios_roles ur on u.id = ur.usuario_id\n" +
                "            inner join seg_roles r on r.id = ur.rol_id\n" +
                "            inner join seg_roles_permisos rp on rp.rol_id = r.id\n" +
                "            inner join seg_permisos p on p.id = rp.permiso_id\n" +
                "            union\n" +
                "            select u.username, 'perm:'+p.nombre as rolename from seg_usuarios u\n" +
                "            inner join seg_usuarios_roles ur on u.id = ur.usuario_id\n" +
                "            inner join seg_roles r on r.id = ur.rol_id\n" +
                "            inner join seg_roles_permisos rp on rp.rol_id = r.id\n" +
                "            inner join seg_permisos p on p.id = rp.permiso_id) resultado";
        usuarioDAO.executeNativeQuery(sql);
    }

    @AfterEach
    void tearDown() {
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @Test
    void findByUsername() {
        List<VUsuarioRol> usuarioRoles = vUsuarioRolDAO.findByUsername(ADMIN1);
        logger.info("Usuarios roles: {}",Arrays.toString(usuarioRoles.toArray()));
        assertThat(usuarioRoles).size().isEqualTo(2);
    }

    private void crearUsuariosRolesPermisos() {
        logger.info("Creando roles usuarios y permisos en la base de datos");
        Rol rol1Admin = new Rol("ADMIN");
        Rol rolUser = new Rol("USER");
        rolDAO.save(rol1Admin);
        rolDAO.save(rolUser);
        Usuario usuario1 = new Usuario(ADMIN1, "12345",
                new HashSet<>(Arrays.asList(rol1Admin, rolUser)));
        Usuario usuario2 = new Usuario(USUARIO1, "12345",
                new HashSet<>(Collections.singletonList(rolUser)));
        usuarioDAO.save(usuario1);
        usuarioDAO.save(usuario2);

        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Tiene permiso de escritura en la ventana 'Editar Menu");

        permisoDAO.save(editarMenuRead);
        permisoDAO.save(editarMenuWrite);

        rol1Admin.setPermisos(new HashSet<>(Arrays.asList(editarMenuRead, editarMenuWrite)));
        rolUser.setPermisos(new HashSet<>(Collections.singletonList(editarMenuRead)));

        rolDAO.update(rol1Admin);
        rolDAO.update(rolUser);
    }
}