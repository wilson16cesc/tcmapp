package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.audit.AuditFieldsInterceptorImpl;
import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.dao.shared.PaginaDAO;
import com.tcm.tcmapp.dao.security.PermisoDAO;
import com.tcm.tcmapp.entity.base.BaseEntity;
import com.tcm.tcmapp.entity.base.BaseEntityIdentity;
import com.tcm.tcmapp.entity.shared.Pagina;
import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.entity.shared.Icono;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class PaginaDAOTest {

    @Inject
    PaginaDAO paginaDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addClass(Pagina.class)
                .addClass(Icono.class)
                .addClass(Permiso.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(PaginaDAO.class)
                .addClass(PermisoDAO.class)
                .addClass(BaseDAO.class)
                .addClass(AuditFieldsInterceptor.class)
                .addClass(AuditFieldsInterceptorImpl.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        //System.out.println(war.toString(true));
        return war;
    }

    @Before
    public void setUp() throws Exception {
        paginaDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        paginaDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @Test
    public void findMaxId() {
        Pagina pagina = new Pagina(2L, "Item 2", "http://item2.com", false, "save", 1L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(pagina);
        Long maxId = paginaDAO.findMaxId();
        assertEquals(2L, maxId.longValue());
    }

    @Test
    public void dadaPaginaConPermisos_cuandoGuardarPagina_entoncesAlConsultarlaDebeContenerLosPermisos() {
        Permiso usuariosRead = new Permiso("UsuariosRead", "Tiene permiso de lectura en la ventana Usuarios");
        Permiso usuariosWrite = new Permiso("UsuariosWrite", "Tiene permiso de escritura en la ventana Usuarios");
        permisoDAO.save(usuariosRead);
        permisoDAO.save(usuariosWrite);

        Pagina pagina = new Pagina(3L, "Usuarios", "/pages/usuarios.xhtml", true, "user-edit", 2L, LocalDateTime.now(), "mfigueroa", true);
        pagina.getPermisos().add(usuariosRead);
        pagina.getPermisos().add(usuariosWrite);

        paginaDAO.save(pagina);

        Pagina paginaResultado = paginaDAO.findByIdWithPermisos(3L);

        assertThat(paginaResultado.getPermisos()).size().isEqualTo(2);

    }

    @Test
    public void dadasPaginasConPermisos_cuandoConsulteTodasLasPaginas_debeDevolverLosPermisos(){
        Permiso usuariosRead = new Permiso("UsuariosRead", "Tiene permiso de lectura en la ventana Usuarios");
        Permiso usuariosWrite = new Permiso("UsuariosWrite", "Tiene permiso de escritura en la ventana Usuarios");
        permisoDAO.save(usuariosRead);
        permisoDAO.save(usuariosWrite);

        Pagina pagina = new Pagina(3L, "Usuarios", "/pages/usuarios.xhtml", true, "user-edit", 2L, LocalDateTime.now(), "mfigueroa", true);
        pagina.getPermisos().add(usuariosRead);
        pagina.getPermisos().add(usuariosWrite);
        paginaDAO.save(pagina);

        List<Pagina> paginasAll = paginaDAO.findAllOrderByIdPadre();
        Pagina paginaResult = paginasAll.get(0);

        assertThat(paginaResult.getPermisos().size()).isEqualTo(2);
        assertThat(paginaResult.getPermisos()).contains(usuariosRead);
    }

}