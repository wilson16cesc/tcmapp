package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.entity.shared.Icono;
import com.tcm.tcmapp.entity.shared.Pagina;
import com.tcm.tcmapp.entity.security.Permiso;
import com.tcm.tcmapp.service.shared.IconosService;
import com.tcm.tcmapp.service.shared.PaginasService;
import com.tcm.tcmapp.service.security.PermisosService;
import com.tcm.tcmapp.view.menu.MenuEditView;
import com.tcm.tcmapp.view.menu.MenuInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;

import javax.faces.context.FacesContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuEditViewTest extends MenuBaseTest {

    @InjectMocks
    final MenuEditView menuEditView = new MenuEditView();
    @Mock
    Logger logger;
    @Mock
    private PaginasService paginasService;
    @Mock
    private MenuCounter menuCounter;
    @Mock
    private PrimeFaces primeFacesMock;
    @Mock
    private PermisosService permisosService;
    @Mock
    private IconosService iconosService;
    @Mock
    private FacesContext facesContextMock;
    private List<Pagina> paginas;
    private TreeNode<MenuInfo> menuRoot;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> paginasAndMenu = cargarPaginasAndMenu();
        paginas = (List<Pagina>) paginasAndMenu.get(PAGINAS);
        menuRoot = (TreeNode<MenuInfo>) paginasAndMenu.get(MENU);
        PrimeFaces.setCurrent(primeFacesMock);
        Faces.setContext(facesContextMock);

    }


    @Test
    public void dadoDatosDePaginas_cuandoInicializaYConsultaMenuRoot_entoncesDevuelveDatosDelMenu() {
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Permite leer la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Permite escribir en la ventana 'Editar Menu");
        List<Permiso> permisos = Arrays.asList(editarMenuRead, editarMenuWrite);
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        given(permisosService.findAllActive()).willReturn(permisos);
        given(iconosService.findAllActive()).willReturn(new ArrayList<>());

        menuEditView.init();

        TreeNode<MenuInfo> menuRootObtenido = menuEditView.getMenuRoot();

        verify(paginasService, times(1)).getPaginasParaMenu();
        verify(permisosService, times(1)).findAllActive();

        TreeNode<MenuInfo> nodoCero = menuRootObtenido.getChildren().get(0);
        TreeNode<MenuInfo> menuItem1 = nodoCero.getChildren().get(0);
        TreeNode<MenuInfo> menuItem2 = menuItem1.getChildren().get(0);
        TreeNode<MenuInfo> menuItem4 = menuItem2.getChildren().get(1);

        assertThat(menuRootObtenido.getChildren().size()).isEqualTo(1);
        assertThat(menuItem4.getData().getName()).isEqualTo("Item 4");
    }

    @Test
    public void dadoMenuExistente_cuandoInvocaagregarActualizarNodoMenu_entoncesAgregaElNuevoNodo() {
        Pagina newPagina = new Pagina("Item 5", "http://item5.com", true, "save", 2L, null, null, null);

        given(menuCounter.getNextId()).willReturn(5L);

        menuEditView.setPaginas(paginas);
        menuEditView.setMenuRoot(menuRoot);
        menuEditView.setPaginaEditar(newPagina);

        menuEditView.agregarActualizarNodoMenu();

        TreeNode<MenuInfo> menuRootModificado = menuEditView.getMenuRoot();

        TreeNode<MenuInfo> menuItem1 = menuRootModificado.getChildren().get(0);
        TreeNode<MenuInfo> menuItem2 = menuItem1.getChildren().get(0);
        TreeNode<MenuInfo> nuevoMenuItem = menuItem2.getChildren().get(2);

        assertThat(nuevoMenuItem.getData().getName()).isEqualTo("Item 5");

    }

    @Test
    public void dadoMenuExistente_cuandoInvoqueGuardarMenu_entoncesGuardarOActualizarLasPaginas() {
        Pagina paginaParaActualizar = new Pagina(4L, "Item 4 actualizado", "http://item4.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
        paginaParaActualizar.setEditado(true);
        Pagina paginaParaInsertar = new Pagina(5L, "Item 5", "http://item5.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
        paginaParaInsertar.setCreado(true);
        paginas.add(paginaParaInsertar);
        paginas.add(paginaParaActualizar);

        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        menuEditView.setPaginas(paginas);

        menuEditView.guardarMenu();

        ArgumentCaptor<List<Pagina>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(paginasService, times(1)).saveOrUpdateAll(argumentCaptor.capture());
        List<Pagina> paginasGuardadas = argumentCaptor.getValue();
        assertThat(paginasGuardadas).contains(paginaParaInsertar);
        assertThat(paginasGuardadas).contains(paginaParaActualizar);
        verify(paginasService, times(1)).getPaginasParaMenu();
    }

    /**
     * el siguiente test utiliza esta estructura de menú
     * 0--1--2
     * |      --3
     * |      --4
     */
    @Test
    public void dadasHojasConNuevosPermisos_cuandoInvoqueGuardarMenu_entoncesLosPermisosDebenPropagarseALasPaginasPadres() {
        Pagina paginaParaInsertar = new Pagina(5L, "Item 5", "http://item5.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Permite leer la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Permite escribir en la ventana 'Editar Menu");
        Permiso usuariosRead = new Permiso("UsuariosRead", "Tiene permiso de lectura en la ventana Usuarios");

        paginas.forEach(pagina -> {
            if (pagina.getId() == 1)
                pagina.getPermisos().add(usuariosRead);
        });

        paginaParaInsertar.getPermisos().add(editarMenuWrite);
        paginaParaInsertar.getPermisos().add(editarMenuRead);

        paginaParaInsertar.setCreado(true);
        paginas.add(paginaParaInsertar);

        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        menuEditView.setPaginas(paginas);

        menuEditView.guardarMenu();

        List<Pagina> paginasResult = menuEditView.getPaginas();
        Pagina pagina1 = paginasResult.stream()
                .filter(pagina -> pagina.getId() == 1)
                .findFirst().orElse(new Pagina());

        Pagina pagina2 = paginasResult.stream()
                .filter(pagina -> pagina.getId() == 2)
                .findFirst().orElse(new Pagina());

        assertThat(pagina1.getPermisos()).contains(editarMenuRead);
        assertThat(pagina1.getPermisos()).contains(editarMenuWrite);
        assertThat(pagina1.getPermisos()).contains(usuariosRead);

        assertThat(pagina2.getPermisos()).contains(editarMenuRead);
        assertThat(pagina2.getPermisos()).contains(editarMenuWrite);
    }

    @Test
    public void dadoMenuExistente_cuandoInvoqueBorrarNodoMenu_entoncesRemoverNodoMenu() {
        Pagina newPagina = new Pagina(5L, "Item 5", "http://item5.com", true, "save", 12L, LocalDateTime.now(), "mfigueroa", true);
        paginas.add(newPagina);
        TreeNode<MenuInfo> menuItem1 = menuRoot.getChildren().get(0);
        TreeNode<MenuInfo> menuItem2 = menuItem1.getChildren().get(0);
        DefaultTreeNode<MenuInfo> nuevoMenuItem = new DefaultTreeNode<>(MenuInfo.fromPagina(newPagina));
        menuItem2.getChildren().add(nuevoMenuItem);

        menuEditView.setSelectedNode(nuevoMenuItem);
        menuEditView.setSelectedPagina(newPagina);
        menuEditView.setPaginas(paginas);
        menuEditView.setMenuRoot(menuRoot);
        menuEditView.setIconos(cargarNombresIconos());


        assertThat(menuItem2.getChildCount()).isEqualTo(3);

        menuEditView.borrarNodoMenu();

        TreeNode<MenuInfo> menuRootModificado = menuEditView.getMenuRoot();
        TreeNode<MenuInfo> menuItemUno = menuRootModificado.getChildren().get(0);
        TreeNode<MenuInfo> menuItemDos = menuItemUno.getChildren().get(0);
        assertThat(menuItemDos.getChildCount()).isEqualTo(2);
    }

    @Test

    public void dadoMenuExistente_cuandoInvoqueNuevoNodoMenu_entoncesObjetoPaginaEditarDebeConfigurarse() {
        Pagina selectedPagina = new Pagina(2L, "Item 2", null, false, "save", 1L, LocalDateTime.now(), "mfigueroa", true);
        menuEditView.setSelectedPagina(selectedPagina);

        menuEditView.nuevoNodoMenu();

        assertThat(menuEditView.getPaginaEditar().getId()).isNull();
        assertThat(selectedPagina.getId()).isEqualTo(menuEditView.getPaginaEditar().getIdPadre());
        verify(primeFacesMock, times(1)).executeScript("PF('dlgCrearPagina').show();");

    }

    @Test
    public void dadoMenuExistente_cuandoInvoqueAgregarActualizarNodoMenu_debeActualizarDatosDeSelectedNodeSiElNodoYaExistia() {
        Pagina paginaOriginal = new Pagina(2L, "Item 1", null, false, "save", 1L, LocalDateTime.now(), "mfigueroa", true);
        Pagina paginaModificada = new Pagina(2L, "Reportes", null, false, "plus", 1L, LocalDateTime.now(), "mfigueroa", true);

        TreeNode<MenuInfo> originalSelectedNode = new DefaultTreeNode<>(MenuInfo.fromPagina(paginaOriginal));

        menuEditView.setSelectedNode(originalSelectedNode);
        menuEditView.setPaginaEditar(paginaModificada);

        menuEditView.agregarActualizarNodoMenu();

        assertThat(paginaModificada.getEditado()).isTrue();
        assertThat(menuEditView.getSelectedNode().getData().getName()).isEqualTo("Reportes");
        assertThat(menuEditView.getSelectedNode().getData().getIcon()).isEqualTo("plus");
    }

    @Test
    public void dadoUnCriterioParaFiltrarIconos_cuandoInvoqueCompleteIcono_entoncesDevuelveListaFiltrada() {
        String criterioFiltro = "rrow";
        menuEditView.setIconos(cargarNombresIconos());

        List<String> iconosFiltrados = menuEditView.completeIcono(criterioFiltro);
        assertThat(iconosFiltrados.size()).isEqualTo(2);
        assertThat(iconosFiltrados.get(0)).isEqualTo("arrows-h");
    }

    private List<Icono> cargarIconos() {
        List<Icono> iconos = new ArrayList<>();
        List<String> nombresIconos = cargarNombresIconos();
        nombresIconos.forEach(nombre -> iconos.add(new Icono(nombre)));
        return iconos;
    }

    private List<String> cargarNombresIconos() {
        return new ArrayList<>(Arrays.asList("sort-alt", "arrows-h", "arrows-v", "pound", "prime",
                "chart-pie", "reddit", "code", "sync", "shopping-bag", "server", "database", "hashtag"));
    }

}
