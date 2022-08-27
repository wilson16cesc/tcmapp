package com.tcm.tcmapp.view;

import com.tcm.tcmapp.business.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuEditViewTest extends MenuBaseTest{

    @Mock
    private PaginasService paginasService;

    private List<Pagina> paginas;

    @InjectMocks
    private MenuEditView menuEditView = new MenuEditView();

    @Before
    public void setUp() throws Exception {
        paginas = cargarPaginas();
    }

    @Test
    public void givenPaginasData_whenGetMenuRoot_thenReturnMenuData(){

        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        menuEditView.init();
        TreeNode<MenuInfo> menuRoot = menuEditView.getMenuRoot();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(3, menuRoot.getChildren().size());
        assertEquals("Item 12", menuRoot.getChildren().get(2).getData().getName());
    }

}
