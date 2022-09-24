package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.audit.AuditFieldsInterceptor;
import com.tcm.tcmapp.audit.AuditFieldsInterceptorImpl;
import com.tcm.tcmapp.entity.BaseEntity;
import com.tcm.tcmapp.entity.BaseEntityIdentity;
import com.tcm.tcmapp.entity.Icono;
import org.assertj.core.api.Assertions;
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
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class IconoDAOTest {

    @Inject
    IconoDAO iconoDAO;

    //HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                //.addAsLibraries(pomFile.resolve("org.mockito:mockito-core").withTransitivity().asFile())
                .addPackage(Assertions.class.getPackage())
                .addClass(Icono.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(IconoDAO.class)
                .addClass(AuditFieldsInterceptor.class)
                .addClass(AuditFieldsInterceptorImpl.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        //war.as(ZipExporter.class).exportTo(new File("/tmp/tcmapp.war"), true);
        return war;
    }

    @Before
    public void setUp() throws Exception {
        //given(request.getRemoteUser()).willReturn("dummy-user1");
        Icono icono = new Icono("icon-name");
        System.out.println("icono::: "+ icono);
        iconoDAO.save(icono);
    }

    @After
    public void tearDown() throws Exception {
        iconoDAO.deleteAll();
    }

    @Test
    public void testFindFirst() {
        Icono primerIcono = iconoDAO.findFirst();
        assertThat(primerIcono).isNotNull();
        assertEquals("icon-name", primerIcono.getNombre());
    }


}
