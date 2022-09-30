package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.menu.MenuModel;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuViewTest extends MenuBaseTest {

    @Mock
    private PaginasService paginasService;

    private List<Pagina> paginas;

    @Mock
    ViewHelper viewHelper;

    @InjectMocks
    private final MenuView menuView = new MenuView();

    @Before
    public void setUp() throws Exception {
        Map<String, Object> paginasAndMenu = cargarPaginasAndMenu();
        paginas = (List<Pagina>) paginasAndMenu.get(PAGINAS);

    }

    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuModel_entoncesDevuelveEstructuraDelMenu() {
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);
        given(viewHelper.hasPermissions(anyList())).willReturn(true);

        menuView.init();
        MenuModel menuModel = menuView.getMenuModel();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(1, menuModel.getElements().size());
        assertEquals("1", menuModel.getElements().get(0).getId());
        assertThat(menuModel.getElements().size()).isEqualTo(1);
    }

}