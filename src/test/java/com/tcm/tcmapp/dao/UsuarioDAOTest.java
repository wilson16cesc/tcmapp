package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UsuarioDAOTest {

    public static final String USER_ROLE = "user_role";
    public static final String ADMIN_ROLE = "admin_role";
    public static final String PASSWORD_SIMPLE = "12345";
    public static final String USUARIO_DEMO = "mfigueroa";
    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    RolDAO rolDAO;

    @Inject
    Pbkdf2PasswordHash passwordHash;

    @Before
    public void setUp() throws Exception {

        Rol rol1 = new Rol(USER_ROLE);
        Rol rol2 = new Rol(ADMIN_ROLE);
        rolDAO.save(rol1);
        rolDAO.save(rol2);
        Usuario usuario1 = new Usuario(USUARIO_DEMO, PASSWORD_SIMPLE,
                new HashSet<>(Arrays.asList(rol1,rol2)));
        usuarioDAO.save(usuario1);
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
    public void findByUsername() {
        Usuario usuario = usuarioDAO.findByUsername(USUARIO_DEMO);
        assertNotNull(usuario);
    }

    @Test
    @Ignore
    public void dadoUsuarioConPasswordEncriptado_cuandoConsultaUsuario_entoncesDebeCoincidirElPassword(){
        Usuario usuario = usuarioDAO.findByUsername(USUARIO_DEMO);

        char[] unencriptedPassword = "12345".toCharArray();
        assertTrue(passwordHash.verify(unencriptedPassword,usuario.getPassword()));

    }
    @Test
    public void dadoUsuariosExistentes_cuandoConsultaUsuariosYRoles_entoncesDebeDevolverUsuariosConSusRoles(){
        List<Usuario> usuariosConRoles = usuarioDAO.findAllWithRoles();

        assertEquals(1, usuariosConRoles.size());
        assertEquals(2, usuariosConRoles.get(0).getRoles().size());
    }

    @Test
    public void dadoUsuarioExistente_cuandoConsultaUsuarioPorUsuarioYContrasena_entoncesDebeDevolverUsuarioConSusRoles() {

        Usuario usuario = usuarioDAO.findWithRoles(USUARIO_DEMO, PASSWORD_SIMPLE);

        assertNotNull(usuario);
        assertEquals(2, usuario.getRoles().size());
    }

    @After
    public void tearDown() throws Exception {
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
    }
}