package com.tcm.tcmapp.view.shared;

import com.tcm.tcmapp.entity.security.Permiso;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ViewHelperTest  {


    public static final String USUARIOS_READ = "UsuariosRead";
    public static final String COMMA_SEPARATED_PERMISSIONS = "PermisosWrite,UsuariosRead";
    @Mock
    private FacesContext facesContextMock;

    @Mock
    private ExternalContext externalContextMock;


    @InjectMocks
    private ViewHelper viewHelper;

    @Before
    public void setUp() {
        Faces.setContext(facesContextMock);
        given(facesContextMock.getExternalContext()).willReturn(externalContextMock);
    }

    @Test
    public void dadoUsuarioConPermisos_cuandoInvocaHasPermission_entoncesDevuelveVerdadero() {
        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(true);
        boolean result = viewHelper.hasPermission(USUARIOS_READ);

        assertThat(result).isTrue();
    }

    @Test
    public void dadoUsuarioSinPermisos_cuandoInvocaHasPermission_entoncesDevuelveFalso() {
        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(false);
        boolean result = viewHelper.hasPermission(USUARIOS_READ);

        assertThat(result).isFalse();
    }

    @Test
    public void dadoUsuarioConPermisos_cuandoInvocaHasPermissions_entoncesDevuelveVerdadero() {

        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(true);
        boolean result = viewHelper.hasPermissions(COMMA_SEPARATED_PERMISSIONS);

        assertThat(result).isTrue();
    }

    @Test
    public void dadoUsuarioSinPermisos_cuandoInvocaHasPermissions_entoncesDevuelveFalso() {
        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(false);
        boolean result = viewHelper.hasPermissions(COMMA_SEPARATED_PERMISSIONS);

        assertThat(result).isFalse();
    }

    @Test
    public void dadoUsuarioConPermisos_cuandoInvocaHasPermissionsConObjetos_entoncesDevuelveVerdadero() {
        Permiso usuariosRead = new Permiso("UsuariosRead", "Tiene permiso de lectura en la ventana 'Usuarios'.");
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        ArrayList<Permiso> permisos = new ArrayList<>(Arrays.asList(usuariosRead, editarMenuRead));

        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(true);
        boolean result = viewHelper.hasPermissions(permisos);

        assertThat(result).isTrue();
    }

    @Test
    public void dadoUsuarioSinPermisos_cuandoInvocaHasPermissionsConObjetos_entoncesDevuelveFalso() {
        Permiso usuariosRead = new Permiso("UsuariosRead", "Tiene permiso de lectura en la ventana 'Usuarios'.");
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        ArrayList<Permiso> permisos = new ArrayList<>(Arrays.asList(usuariosRead, editarMenuRead));

        given(externalContextMock.isUserInRole("perm:"+USUARIOS_READ)).willReturn(false);
        boolean result = viewHelper.hasPermissions(permisos);

        assertThat(result).isFalse();
    }

    public void testHasPermission() {
    }

    public void testTestHasPermissions() {
    }
}