package com.tcm.tcmapp.view.security;

import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.service.security.PermisosService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PermisosViewTest {

    public static final String USUARIOS_READ = "UsuariosRead";
    public static final String EDITAR_MENU_WRITE = "EditarMenuWrite";
    @Mock
    Logger logger;
    @Mock
    private FacesContext facesContextMock;
    @Mock
    private ExternalContext externalContextMock;
    @Mock
    private PermisosService permisosService;
    @InjectMocks
    private PermisosView permisosView;

    @Before
    public void setUp() throws Exception {
        Faces.setContext(facesContextMock);
        given(facesContextMock.getExternalContext()).willReturn(externalContextMock);
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsCrear_entoncesSelectedPermisoNoEsNulo() {
        Map<String, String> requestParameterMap = Collections.singletonMap("accion", "crear");
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);

        permisosView.init();

        Permiso selectedPermiso = permisosView.getSelectedPermiso();

        assertThat(selectedPermiso).isNotNull();
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsEditar_entoncesCargarPermisoPorId() {
        Map<String, String> requestParameterMap = new HashMap<>();
        requestParameterMap.put("accion","editar");
        requestParameterMap.put("permisoId","1");
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);

        Permiso usuariosRead = new Permiso(USUARIOS_READ, "Tiene permiso de lectura en la ventana 'Usuarios'.");
        usuariosRead.setId(1L);
        given(permisosService.findById(1L)).willReturn(usuariosRead);


        permisosView.init();

        Permiso selectedPermiso = permisosView.getSelectedPermiso();

        assertThat(selectedPermiso.getNombre()).isEqualTo(USUARIOS_READ);
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsNula_entoncesCargarTodosLosPermisos() {
        Map<String, String> requestParameterMap = Collections.emptyMap();
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);

        Permiso usuariosRead = new Permiso(USUARIOS_READ, "Tiene permiso de lectura en la ventana 'Usuarios'.");
        usuariosRead.setId(1L);
        Permiso editarMenuWrite = new Permiso(EDITAR_MENU_WRITE, "Permite escribir en la ventana 'Editar Menu");
        editarMenuWrite.setId(2L);

        List<Permiso> permisos = new ArrayList<>(Arrays.asList(usuariosRead, editarMenuWrite));
        given(permisosService.findAll()).willReturn(permisos);

        permisosView.init();

        List<Permiso> permisosResult = permisosView.getPermisos();

        assertThat(permisosResult)
                .contains(usuariosRead, editarMenuWrite);
    }

    @Test
    public void cuandoInvocaGuardarPermiso_entoncesDebeLlamarMetotoUpdate(){
        Permiso usuariosRead = new Permiso(USUARIOS_READ, "Tiene permiso de lectura en la ventana 'Usuarios'.");
        usuariosRead.setId(1L);

        permisosView.setSelectedPermiso(usuariosRead);
        permisosView.guardarPermiso();

        verify(permisosService).update(usuariosRead);
    }

    @Test
    public void cuandoInvocaNuevoPermiso_entoncesDebeLimpiarElObjetoSelectedPermiso(){
        Permiso usuariosRead = new Permiso(USUARIOS_READ, "Tiene permiso de lectura en la ventana 'Usuarios'.");
        usuariosRead.setId(1L);

        permisosView.setSelectedPermiso(usuariosRead);
        permisosView.nuevoPermiso();
        Permiso selectedPermiso = permisosView.getSelectedPermiso();

        assertThat(selectedPermiso.getNombre()).isNull();

    }
}