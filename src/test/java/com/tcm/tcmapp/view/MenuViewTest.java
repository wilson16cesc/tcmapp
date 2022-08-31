package com.tcm.tcmapp.view;

import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.menu.MenuModel;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuViewTest extends MenuBaseTest {

    @Mock
    private PaginasService paginasService;

    private List<Pagina> paginas;

    @InjectMocks
    private final MenuView menuView = new MenuView();

    @Before
    public void setUp() throws Exception {
        paginas = cargarPaginas();
    }


    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuModel_entoncesDevuelveEstructuraDelMenu() {
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        menuView.init();
        MenuModel menuModel = menuView.getMenuModel();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(3, menuModel.getElements().size());
        assertEquals("12", menuModel.getElements().get(2).getId());

    }

}