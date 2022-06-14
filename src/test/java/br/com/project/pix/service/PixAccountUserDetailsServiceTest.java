package br.com.project.pix.service;

import br.com.project.pix.config.ProjectionConfigTest;
import br.com.project.pix.dto.projection.PixAccountUserDetailsProjection;
import br.com.project.pix.exception.validations.DataNotFoundException;
import br.com.project.pix.exception.validations.FilterDateException;
import br.com.project.pix.exception.validations.FilterOnlyIdException;
import br.com.project.pix.exception.validations.KeyInactiveException;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.repository.PixAccountUserDetailsRepository;
import br.com.project.pix.service.helper.ValidationsService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.project.pix.helper.Helper.ACCOUNT_HOLDER_NAME;
import static br.com.project.pix.helper.Helper.ACCOUNT_TYPE_CURRENT;
import static br.com.project.pix.helper.Helper.ACCOUNT_TYPE_SAVINGS;
import static br.com.project.pix.helper.Helper.ID;
import static br.com.project.pix.helper.Helper.KEY_TYPE_EMAIL;
import static br.com.project.pix.helper.Helper.KEY_VALUE_CELULAR;
import static br.com.project.pix.helper.Helper.PERSON_LEGAL_TYPE;
import static br.com.project.pix.helper.Helper.PERSON_NATURAL_TYPE;
import static br.com.project.pix.helper.Helper.createPixAccountUserDetailsProjection;
import static br.com.project.pix.helper.Helper.createPixCelularAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixEmailAccountUserDetails;
import static br.com.project.pix.helper.Helper.inactivePixEmailAccountUserDetails;
import static br.com.project.pix.helper.Helper.updatePixEmailAccountUserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProjectionConfigTest.class})
public class PixAccountUserDetailsServiceTest {

    @Autowired
    private ProjectionFactory projectionFactory;

    @InjectMocks
    private PixAccountUserDetailsService pixAccountUserDetailsServiceMock;

    @Mock
    private PixAccountUserDetailsRepository pixAccountUserDetailsRepositoryMock;

    @Mock
    private ValidationsService validationsServiceMock;

    @Test
    @DisplayName("Deve inserir um objeto do tipo PixAccountUserDetails")
    public void shouldCreateNewPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.save(any())).thenReturn(createPixEmailAccountUserDetails());

        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsServiceMock.create(createPixEmailAccountUserDetails());

        assertNotNull(pixAccountUserDetails);
        assertEquals(KEY_TYPE_EMAIL, pixAccountUserDetails.getKeyType());
        assertEquals(PERSON_NATURAL_TYPE, pixAccountUserDetails.getPersonType());
        assertEquals(ACCOUNT_TYPE_SAVINGS, pixAccountUserDetails.getAccountType());
        verify(validationsServiceMock, times(1)).validateCreatePixAccountUserDetails(any(PixAccountUserDetails.class), eq(pixAccountUserDetailsRepositoryMock));
        verify(validationsServiceMock, times(0)).validateUpdatePixAccountUserDetails(any(), any());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).save(any(PixAccountUserDetails.class));
    }

    @Test
    @DisplayName("Deve atualizar um objeto do tipo PixAccountUserDetails")
    public void shouldUpdatePixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.save(any())).thenReturn(updatePixEmailAccountUserDetails());

        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsServiceMock.update(updatePixEmailAccountUserDetails());

        assertNotNull(pixAccountUserDetails);
        assertEquals(PERSON_LEGAL_TYPE, pixAccountUserDetails.getPersonType());
        assertEquals(ACCOUNT_TYPE_CURRENT, pixAccountUserDetails.getAccountType());
        verify(validationsServiceMock, times(0)).validateCreatePixAccountUserDetails(any(), any());
        verify(validationsServiceMock, times(1)).validateUpdatePixAccountUserDetails(any(PixAccountUserDetails.class), eq(pixAccountUserDetailsRepositoryMock));
        verify(pixAccountUserDetailsRepositoryMock, times(1)).save(any(PixAccountUserDetails.class));
    }

    @Test
    @DisplayName("Deve inativar um objeto do tipo PixAccountUserDetails")
    public void shouldDeletePixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.save(any())).thenReturn(inactivePixEmailAccountUserDetails());

        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsServiceMock.delete(updatePixEmailAccountUserDetails());

        assertNotNull(pixAccountUserDetails);
        assertNotNull(pixAccountUserDetails.getInactiveKeyDateTime());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).save(any(PixAccountUserDetails.class));
    }

    @Test
    @DisplayName("Deve retornar uma exception KeyInactiveException ao tentar inativar um objeto do tipo PixAccountUserDetails no banco de dados")
    public void shouldReturnKeyInactiveException() {

        assertThrows(KeyInactiveException.class, () -> pixAccountUserDetailsServiceMock.delete(inactivePixEmailAccountUserDetails()));
    }

    @Test
    public void shouldFindProjectionByIdPixAccountUserDetails() {

        // when
        when(pixAccountUserDetailsRepositoryMock.findProjectionById(any())).thenReturn(Optional.of(createPixAccountUserDetailsProjection(projectionFactory)));

        PixAccountUserDetailsProjection pixAccountUserDetailsProjection = pixAccountUserDetailsServiceMock.findProjectionById(ID);

        assertNotNull(pixAccountUserDetailsProjection);
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findProjectionById(ID);
    }

    @Test
    @DisplayName("Deve retornar uma exception KeyInactiveException ao procurar um id no banco de dados inexistente por projection id")
    public void shouldDataNotFoundExceptionInFindProjectionByIdPixAccountUserDetails() {

        when(pixAccountUserDetailsRepositoryMock.findProjectionById(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> pixAccountUserDetailsServiceMock.findProjectionById(ID));
    }

    @Test
    @DisplayName("Deve buscar um objeto por id do tipo PixAccountUserDetails")
    public void shouldFindByIdPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findById(any())).thenReturn(Optional.of(createPixCelularAccountUserDetails()));

        PixAccountUserDetails pixAccountUserDetails = pixAccountUserDetailsServiceMock.findById(ID);

        assertNotNull(pixAccountUserDetails);
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Deve retornar uma exception KeyInactiveException ao procurar um id no banco de dados inexistente por id")
    public void shouldDataNotFoundExceptionInFindByIdPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> pixAccountUserDetailsServiceMock.findById(ID));
    }

    @Test
    @DisplayName("Deve buscar um objeto do tipo PixAccountUserDetails usando os parametros")
    public void shouldFindByIdUsuFindAllWithParametersPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        List<PixAccountUserDetailsProjection> listPixAccountUserDetails = pixAccountUserDetailsServiceMock.findAllWithParameters(null, null, null, null, ID.toString(), null, null);

        assertNotNull(listPixAccountUserDetails);
        assertEquals(1, listPixAccountUserDetails.size());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findAllWithParameters(null, null, null, null, ID.toString(), null, null);
    }

    @Test
    @DisplayName("Deve retornar FilterOnlyIdException ao buscar um objeto do tipo PixAccountUserDetails usando os parametros, por ter informado o id e outro filtro")
    public void shouldFilterOnlyIdException() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));


        assertThrows(FilterOnlyIdException.class, () -> pixAccountUserDetailsServiceMock.findAllWithParameters(KEY_TYPE_EMAIL, null, null, null, ID.toString(), null, null));
    }

    @Test
    @DisplayName("Deve buscar um objeto do tipo PixAccountUserDetails usando os parametros valor da chave e nome do correntista")
    public void shouldFindByIdUsuFindAllWithParametersKeyValueAndAccountHolderNamePixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        List<PixAccountUserDetailsProjection> listPixAccountUserDetails = pixAccountUserDetailsServiceMock.findAllWithParameters(KEY_VALUE_CELULAR, null, null, ACCOUNT_HOLDER_NAME, null, null, null);

        assertNotNull(listPixAccountUserDetails);
        assertEquals(1, listPixAccountUserDetails.size());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findAllWithParameters(KEY_VALUE_CELULAR, null, null, ACCOUNT_HOLDER_NAME, null, null, null);
    }

    @Test
    @DisplayName("Deve buscar um objeto do tipo PixAccountUserDetails usando o data de ativação da chave")
    public void shouldFindByIdUsuFindAllWithParametersDateActiveKeyPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        List<PixAccountUserDetailsProjection> listPixAccountUserDetails = pixAccountUserDetailsServiceMock.findAllWithParameters(null, null, null, null, null, "12-06-2022", null);

        assertNotNull(listPixAccountUserDetails);
        assertEquals(1, listPixAccountUserDetails.size());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findAllWithParameters(null, null, null, null, null, "12-06-2022%", null);
    }

    @Test
    @DisplayName("Deve buscar um objeto do tipo PixAccountUserDetails usando o data de inativação da chave")
    public void shouldFindByIdUsuFindAllWithParametersDateInactiveKeyPixAccountUserDetails() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        List<PixAccountUserDetailsProjection> listPixAccountUserDetails = pixAccountUserDetailsServiceMock.findAllWithParameters(null, null, null, null, null, null, "14-06-2022");

        assertNotNull(listPixAccountUserDetails);
        assertEquals(1, listPixAccountUserDetails.size());
        verify(pixAccountUserDetailsRepositoryMock, times(1)).findAllWithParameters(null, null, null, null, null, null, "14-06-2022%");
    }

    @Test
    @DisplayName("Deve retornar FilterDateException ao buscar um objeto do tipo PixAccountUserDetails usando os parametros de data de ativação e data de inativação simultaneamente")
    public void shouldFilterDateException() {

        //when
        when(pixAccountUserDetailsRepositoryMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));


        assertThrows(FilterDateException.class, () -> pixAccountUserDetailsServiceMock.findAllWithParameters(null, null, null, null, null, "12-06-2022", "14-06-2022"));
    }

}
