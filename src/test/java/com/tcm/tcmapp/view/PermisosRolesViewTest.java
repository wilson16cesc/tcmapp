package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.service.PermisosService;
import com.tcm.tcmapp.service.RolesService;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PermisosRolesViewTest {

    @Mock
    RolesService rolesService;

    @Mock
    PermisosService permisosService;

    @Mock
    private PrimeFaces primeFacesMock;

    @Mock
    private FacesContext facesContextMock;

    @Mock
    Logger logger;

    @InjectMocks
    PermisosRolesView permisosRolesView = new PermisosRolesView();



    @Before
    public void setUp() {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void cuandoInvocaGuardarRol_entoncesInvocaServicioGuardarRolYCargaDatosDeNuevoRol() {
        PrimeFaces.setCurrent(primeFacesMock);
        Faces.setContext(facesContextMock);
        List<Rol> roles = new ArrayList<>(Arrays.asList(new Rol("ADMIN")));
        Rol rolEditar = new Rol("OPERADOR");

        permisosRolesView.setRoles(roles);
        permisosRolesView.setRolEditar(rolEditar);

        permisosRolesView.guardarRol();

        verify(rolesService, times(1)).save(rolEditar);
        assertThat(permisosRolesView.getSelectedRol()).isEqualTo(rolEditar);
        assertThat(permisosRolesView.getRoles()).contains(rolEditar);

    }

    @Test
    public void cuandoInvoqueInit_entoncesInicializarDatosDelModulo() {
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Permite leer la ventana 'Editar Menu'.");
        List<Permiso> permisos = new ArrayList<>(Arrays.asList(editarMenuRead));
        List<Rol> roles = new ArrayList<>(Arrays.asList(new Rol("ADMIN", permisos)));
        given(permisosService.findAllActive()).willReturn(permisos);
        given(rolesService.findAllActiveWithPermisos()).willReturn(roles);

        permisosRolesView.init();

        verify(permisosService, times(1)).findAllActive();
        verify(rolesService, times(1)).findAllActiveWithPermisos();
        assertThat(permisosRolesView.getPermisosModel().getSource()).contains(editarMenuRead);
    }

    @Test
    public void cuandoInvocaGuardarPermisos_entoncesInvocaActualizarRolYMuestraMensajeInformandolo() {
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Permite leer la ventana 'Editar Menu'.");
        List<Permiso> permisos = new ArrayList<>(Arrays.asList(editarMenuRead));
        Rol selectedRol = new Rol("ADMIN", new ArrayList<>());
        PrimeFaces.setCurrent(primeFacesMock);
        Faces.setContext(facesContextMock);

        DualListModel<Permiso> permisosModel = new DualListModel<>(new ArrayList<>(), new ArrayList<>(permisos));

        permisosRolesView.setSelectedRol(selectedRol);
        permisosRolesView.setPermisosModel(permisosModel);

        permisosRolesView.guardarPermisos();

        verify(rolesService, times(1)).update(selectedRol);

    }
}