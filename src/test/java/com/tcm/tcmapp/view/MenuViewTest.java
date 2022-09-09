package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.model.menu.MenuModel;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MenuViewTest extends MenuBaseTest {

    @Mock
    private PaginasService paginasService;

    private List<Pagina> paginas;

    @InjectMocks
    private final MenuView menuView = new MenuView();

    @BeforeEach
    public void setUp() throws Exception {
        Map<String, Object> paginasAndMenu = cargarPaginasAndMenu();
        paginas = (List<Pagina>) paginasAndMenu.get(PAGINAS);

    }


    @Test
    public void dadoDatosDePaginas_cuandoInvocaGetMenuModel_entoncesDevuelveEstructuraDelMenu() {
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        menuView.init();
        MenuModel menuModel = menuView.getMenuModel();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(1, menuModel.getElements().size());
        assertEquals("1", menuModel.getElements().get(0).getId());
        assertThat(menuModel.getElements().size()).isEqualTo(1);
    }

}