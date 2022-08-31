package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.TreeNode;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MenuEditViewTest extends MenuBaseTest {

    @Mock
    private PaginasService paginasService;

    @Mock
    private MenuCounter menuCounter;

    @Mock
    private PrimeFaces primeFacesMock;

    @Mock
    private FacesContext facesContextMock;


    private List<Pagina> paginas;

    @InjectMocks
    final MenuEditView menuEditView = new MenuEditView();

    @Before
    public void setUp() throws Exception {
        paginas = cargarPaginas();
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        PrimeFaces.setCurrent(primeFacesMock);
        //doNothing().when(primeFacesMock).executeScript(anyString());
        Faces.setContext(facesContextMock);
    }

    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuRoot_entoncesDevuelveDatosDelMenu() {

        menuEditView.init();
        TreeNode<MenuInfo> menuRoot = menuEditView.getMenuRoot();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(3, menuRoot.getChildren().size());
        assertEquals("Item 12", menuRoot.getChildren().get(2).getData().getName());
    }

    @Test
    public void dadoMenuExistente_cuandoInvocaAgregarNuevoItem_entoncesAgregaElNuevoItem() {
        Pagina newPagina = new Pagina(19L, "Item 19", "http://item19.com", true, "pi pi-save", 16L, null, null, null);

        given(menuCounter.getNextId()).willReturn(19L);

        menuEditView.init();
        menuEditView.setNewPagina(newPagina);

        menuEditView.agregarNodoMenu();

        TreeNode<MenuInfo> menuRoot = menuEditView.getMenuRoot();

        TreeNode<MenuInfo> nodoDoce = menuRoot.getChildren().get(2);
        TreeNode<MenuInfo> nodoDieciseis = nodoDoce.getChildren().get(1);
        TreeNode<MenuInfo> nuevoNodo = nodoDieciseis.getChildren().get(2);

        assertEquals("Item 19", nuevoNodo.getData().getName());

    }

    @Test
    public void dadoMenuExistente_cuandoInvoqueGuardarMenu_entoncesGuardarNuevasPaginas() {
        Pagina nuevaPagina19 = new Pagina(19L, "Item 17", "http://item17.com", true, "pi pi-save", 16L, LocalDateTime.now(), "mfigueroa", true);
        Pagina nuevaPagina20 = new Pagina(20L, "Item 18", "http://item18.com", true, "pi pi-save", 16L, LocalDateTime.now(), "mfigueroa", true);
        paginas.add(nuevaPagina19);
        paginas.add(nuevaPagina20);

        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        menuEditView.init();
        menuEditView.guardarMenu();

        verify(paginasService, times(1)).saveAll(Mockito.anyList());
        verify(paginasService, times(2)).getPaginasParaMenu();
    }
}
