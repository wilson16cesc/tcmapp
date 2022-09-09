package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.DatosAplicacion;
import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.entity.Icono;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.context.FacesContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuEditViewTest extends MenuBaseTest {

    @InjectMocks
    final MenuEditView menuEditView = new MenuEditView();
    @Mock
    private PaginasService paginasService;
    @Mock
    private DatosAplicacion datosAplicacion;
    @Mock
    private MenuCounter menuCounter;
    @Mock
    private PrimeFaces primeFacesMock;
    @Mock
    private FacesContext facesContextMock;
    private List<Pagina> paginas;
    private List<Icono> iconos;
    private TreeNode<MenuInfo> menuRoot;

    @Before
    public void setUp() throws Exception {

        Map<String, Object> paginasAndMenu = cargarPaginasAndMenu();
        paginas = (List<Pagina>) paginasAndMenu.get(PAGINAS);
        menuRoot = (TreeNode<MenuInfo>) paginasAndMenu.get(MENU);
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        iconos = cargarIconos();
        given(datosAplicacion.getIconos()).willReturn(iconos);
        PrimeFaces.setCurrent(primeFacesMock);
        //doNothing().when(primeFacesMock).executeScript(anyString());
        Faces.setContext(facesContextMock);
    }


    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuRoot_entoncesDevuelveDatosDelMenu() {

        menuEditView.init();

        TreeNode<MenuInfo> menuRootObtenido = menuEditView.getMenuRoot();

        verify(paginasService, times(1)).getPaginasParaMenu();

        TreeNode<MenuInfo> menuItem1 = menuRootObtenido.getChildren().get(0);
        TreeNode<MenuInfo> menuItem2 = menuItem1.getChildren().get(0);
        TreeNode<MenuInfo> menuItem4 = menuItem2.getChildren().get(1);

        assertEquals(1, menuRootObtenido.getChildren().size());
        assertEquals("Item 4", menuItem4.getData().getName());
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

        assertEquals("Item 5", nuevoMenuItem.getData().getName());

    }

    @Test
    public void dadoMenuExistente_cuandoInvoqueGuardarMenu_entoncesGuardarOActualizarLasPaginas() {
        Pagina paginaParaActualizar = new Pagina(4L, "Item 4 actualizado", "http://item4.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
        paginaParaActualizar.setEditado(true);
        Pagina paginaParaInsertar = new Pagina("Item 5", "http://item5.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
        paginaParaInsertar.setCreado(true);
        paginas.add(paginaParaInsertar);
        paginas.add(paginaParaActualizar);

        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        menuEditView.setPaginas(paginas);

        menuEditView.guardarMenu();

        verify(paginasService, times(1)).saveOrUpdateAll(Mockito.anyList());
        verify(paginasService, times(1)).getPaginasParaMenu();
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


        assertEquals(3, menuItem2.getChildCount());

        menuEditView.borrarNodoMenu();

        TreeNode<MenuInfo> menuRootModificado = menuEditView.getMenuRoot();
        TreeNode<MenuInfo> menuItemUno = menuRootModificado.getChildren().get(0);
        TreeNode<MenuInfo> menuItemDos = menuItemUno.getChildren().get(0);

        assertEquals(2, menuItemDos.getChildCount());
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
