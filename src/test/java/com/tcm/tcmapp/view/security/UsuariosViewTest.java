package com.tcm.tcmapp.view.security;

import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.entity.security.Rol;
import com.tcm.tcmapp.entity.security.Usuario;
import com.tcm.tcmapp.service.security.RolesService;
import com.tcm.tcmapp.service.security.UsuariosService;
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
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.*;

import static com.tcm.tcmapp.view.security.PermisosViewTest.USUARIOS_READ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsuariosViewTest {
    public static final String NOMBRE_USUARIO = "usuario1";
    @Mock
    Logger logger;
    @Mock
    private RolesService rolesService;
    @Mock
    private FacesContext facesContextMock;
    @Mock
    private ExternalContext externalContextMock;
    @Mock
    private UsuariosService usuariosService;
    @Mock
    private Pbkdf2PasswordHash passwordHash;

    @InjectMocks
    private UsuariosView usuariosView;

    @Before
    public void setUp() throws Exception {
        Faces.setContext(facesContextMock);
        given(facesContextMock.getExternalContext()).willReturn(externalContextMock);
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsCrear_entoncesInstanciaSelectedPermisoYCargaLosRoles() {
        Map<String, String> requestParameterMap = Collections.singletonMap("accion", "crear");
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);

        List<Rol> rolesConPermisos = obtenerRolesConPermisos();

        given(rolesService.findAllActiveWithPermisos()).willReturn(rolesConPermisos);

        usuariosView.init();

        Usuario selectedUsuario = usuariosView.getSelectedUsuario();
        List<Rol> roles = usuariosView.getRoles();

        assertThat(selectedUsuario).isNotNull();
        assertThat(roles).size().isEqualTo(2);
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsEditar_entoncesCargarUsuarioPorIdYCargaRoles() {
        Map<String, String> requestParameterMap = new HashMap<>();
        requestParameterMap.put("accion","editar");
        requestParameterMap.put("userId","1");
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);
        List<Rol> roles = obtenerRolesConPermisos();
        Usuario usuario = new Usuario(NOMBRE_USUARIO, "password1", "Nombre", roles);
        usuario.setId(1L);
        given(usuariosService.findById(1L)).willReturn(usuario);


        usuariosView.init();

        Usuario selectedUsuario = usuariosView.getSelectedUsuario();

        assertThat(selectedUsuario.getUsername()).isEqualTo(NOMBRE_USUARIO);
        assertThat(selectedUsuario.getRoles()).size().isEqualTo(2);
        assertThat(selectedUsuario.getRoles().get(0).getNombre()).isEqualTo("ADMIN");
    }

    @Test
    public void dadaInvocacionDeMetodoInit_cuandoAccionEsNula_entoncesCargarTodosLosUsuarios() {
        Map<String, String> requestParameterMap = Collections.emptyMap();
        given(externalContextMock.getRequestParameterMap()).willReturn(requestParameterMap);

        Usuario usuario1 = new Usuario("admin", "12345", "Pedro Perez", new ArrayList<>());
        Usuario usuario2 = new Usuario("usuario", "12345", "Juan Perez", new ArrayList<>());


        ArrayList<Usuario> usuarios = new ArrayList<>(Arrays.asList(usuario1, usuario2));
        given(usuariosService.findAll()).willReturn(usuarios);

        usuariosView.init();


        List<Usuario> usuariosResult = usuariosView.getUsuarios();

        assertThat(usuariosResult)
                .contains(usuario1, usuario2);
    }

    private List<Rol> obtenerRolesConPermisos() {
        Rol rolAdmin = new Rol("ADMIN");
        Rol rolUsuario = new Rol("USER");
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Tiene permiso de escritura en la ventana 'Editar Menu");
        rolAdmin.setPermisos(Arrays.asList(editarMenuRead, editarMenuWrite));
        rolUsuario.setPermisos(Collections.singletonList(editarMenuRead));
        return Arrays.asList(rolAdmin, rolUsuario);
    }
    @Test
    public void cuandoInvoqueCompleteRol_entoncesDebeDevolverListaFiltradaPorElCriterioDado(){
        Rol rolAdmin = new Rol("ADMIN");
        Rol rolUser = new Rol("USER");
        List<Rol> roles = Arrays.asList(rolAdmin, rolUser);
        usuariosView.setRoles(roles);
        List<Rol> listResult = usuariosView.completeRol("min");

        assertThat(listResult).contains(rolAdmin);
    }
    @Test
    public void cuandoInvoqueGuardarUsuario_entoncesDebeEncriptarPasswordYActualizarUsuario(){

        Usuario selectedUsuario = new Usuario("username", "pass", "nombre", new ArrayList<>());
        usuariosView.setSelectedUsuario(selectedUsuario);
        usuariosView.guardarUsuario();

        verify(passwordHash).generate(any(char[].class));
        verify(usuariosService).update(selectedUsuario);
    }
    @Test
    public void cuandoInvoqueNuevoUsuario_entoncesLimpiarDatosUsuario(){
        Usuario selectedUsuario = new Usuario("username", "pass", "nombre", new ArrayList<>());
        usuariosView.setSelectedUsuario(selectedUsuario);

        usuariosView.nuevoUsuario();

        Usuario selectedUsuarioResult = usuariosView.getSelectedUsuario();

        assertThat(selectedUsuarioResult.getNombre()).isNull();
    }

}
