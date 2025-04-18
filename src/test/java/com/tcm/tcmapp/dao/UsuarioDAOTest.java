package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.audit.AuditFieldsInterceptorImpl;
import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.dao.security.RolDAO;
import com.tcm.tcmapp.dao.security.UsuarioDAO;
import com.tcm.tcmapp.entity.base.BaseEntity;
import com.tcm.tcmapp.entity.base.BaseEntityIdentity;
import com.tcm.tcmapp.entity.shared.Pagina;
import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.entity.security.Rol;
import com.tcm.tcmapp.entity.security.Usuario;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addClass(Usuario.class)
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(Pagina.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(UsuarioDAO.class)
                .addClass(RolDAO.class)
                .addClass(BaseDAO.class)
                .addClass(AuditFieldsInterceptor.class)
                .addClass(AuditFieldsInterceptorImpl.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        return war;
    }

    @Before
    public void setUp() throws Exception {

        Rol rol1 = new Rol(USER_ROLE);
        Rol rol2 = new Rol(ADMIN_ROLE);
        rolDAO.save(rol1);
        rolDAO.save(rol2);
        Usuario usuario1 = new Usuario(USUARIO_DEMO, PASSWORD_SIMPLE, "Pedro Perez",
                Arrays.asList(rol1, rol2));
        usuarioDAO.save(usuario1);
    }

    @After
    public void tearDown() throws Exception {
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
    }

    @Test
    public void findByUsername() {
        Usuario usuario = usuarioDAO.findByUsername(USUARIO_DEMO);
        assertThat(usuario).isNotNull();
    }

    @Test
    public void dadoUsuariosExistentes_cuandoConsultaUsuariosYRoles_entoncesDebeDevolverUsuariosConSusRoles() {
        List<Usuario> usuariosConRoles = usuarioDAO.findAllWithRoles();

        assertThat(usuariosConRoles).size().isEqualTo(1);
        assertThat(usuariosConRoles.get(0).getRoles()).size().isEqualTo(2);
    }

    @Test
    public void dadoUsuarioExistente_cuandoConsultaUsuarioPorUsuarioYContrasena_entoncesDebeDevolverUsuarioConSusRoles() {

        Usuario usuario = usuarioDAO.findByUsernameWithRoles(USUARIO_DEMO, PASSWORD_SIMPLE);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getRoles()).size().isEqualTo(2);
    }
}