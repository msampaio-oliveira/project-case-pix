package br.com.project.pix.controller;

import br.com.project.pix.config.ProjectionConfigTest;
import br.com.project.pix.model.PixAccountUserDetails;
import br.com.project.pix.service.PixAccountUserDetailsService;
import br.com.project.pix.utils.NonNullBeanUtilsBean;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static br.com.project.pix.Paths.PIX_ACCOUNT_USER_DETAILS;
import static br.com.project.pix.helper.Helper.ACCOUNT_HOLDER_NAME;
import static br.com.project.pix.helper.Helper.ACCOUNT_NUMBER;
import static br.com.project.pix.helper.Helper.ACCOUNT_TYPE_CURRENT;
import static br.com.project.pix.helper.Helper.AGENCY_ACCOUNT;
import static br.com.project.pix.helper.Helper.HOLDER_LAST_NAME;
import static br.com.project.pix.helper.Helper.KEY_TYPE_CPF;
import static br.com.project.pix.helper.Helper.KEY_TYPE_EMAIL;
import static br.com.project.pix.helper.Helper.KEY_VALUE_CPF;
import static br.com.project.pix.helper.Helper.KEY_VALUE_EMAIL;
import static br.com.project.pix.helper.Helper.PERSON_NATURAL_TYPE;
import static br.com.project.pix.helper.Helper.createPixAccountUserDetailsProjection;
import static br.com.project.pix.helper.Helper.createPixCpfAccountUserDetails;
import static br.com.project.pix.helper.Helper.createPixEmailAccountUserDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PixAccountUserDetailsController.class)
@ContextConfiguration(classes = {ProjectionConfigTest.class})
@ComponentScan(basePackageClasses = PixAccountUserDetailsController.class,
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = PixAccountUserDetailsController.class)})
public class PixAccountUserDetailsControllerTest extends AbstractRestControllerMVCTest {


    private static final String ID_PARAM = "id";
    private static final String KEY_TYPE_PARAM = "keyType";
    private static final String KEY_VALUE_PARAM = "keyValue";
    private static final String ACCOUNT_TYPE_PARAM = "accountType";
    private static final String AGENCY_NUMBER_PARAM = "agencyNumber";
    private static final String ACCOUNT_NUMBER_PARAM = "accountNumber";
    private static final String ACCOUNT_HOLDER_NAME_PARAM = "accountHolderName";
    private static final String ACCOUNT_HOLDER_LAST_NAME_PARAM = "accountHolderLastName";
    private static final String PERSON_TYPE_PARAM = "personType";
    private static final String ID = "1234";

    private static final Long ID_FIND = 2389832L;

    @MockBean
    private PixAccountUserDetailsService pixAccountUserDetailsServiceMock;

    @MockBean
    private NonNullBeanUtilsBean utilsBeanMock;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectionFactory projectionFactory;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void shouldFindById() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(ID_PARAM, ID);

        when(pixAccountUserDetailsServiceMock.findById(Long.parseLong(ID))).thenReturn(createPixEmailAccountUserDetails());

        performGetSuccessfully(PIX_ACCOUNT_USER_DETAILS + "/{id}", ID).andExpect(status().isOk());

        verify(pixAccountUserDetailsServiceMock, times(1)).findProjectionById(Long.parseLong(ID));
    }

    @Test
    public void shouldNotFoundFindAll() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(KEY_VALUE_PARAM, KEY_VALUE_CPF);
        params.add(AGENCY_NUMBER_PARAM, AGENCY_ACCOUNT);
        params.add(ACCOUNT_NUMBER_PARAM, ACCOUNT_NUMBER);
        params.add(ACCOUNT_HOLDER_NAME_PARAM, ACCOUNT_HOLDER_NAME);

        when(pixAccountUserDetailsServiceMock.findAllWithParameters(params.getFirst(KEY_VALUE_PARAM),
                params.getFirst(AGENCY_NUMBER_PARAM), params.getFirst(ACCOUNT_NUMBER_PARAM),
                params.getFirst(ACCOUNT_HOLDER_NAME_PARAM), null, null, null))
                .thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        mockMvc.perform(get(PIX_ACCOUNT_USER_DETAILS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFindAll() throws Exception {

        when(pixAccountUserDetailsServiceMock.findAllWithParameters(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(createPixAccountUserDetailsProjection(projectionFactory)));

        mockMvc.perform(get(PIX_ACCOUNT_USER_DETAILS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(pixAccountUserDetailsServiceMock, times(1)).findAllWithParameters(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    public void shouldCreate() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(KEY_TYPE_PARAM, KEY_TYPE_CPF);
        params.add(KEY_VALUE_PARAM, KEY_VALUE_CPF);
        params.add(ACCOUNT_TYPE_PARAM, ACCOUNT_TYPE_CURRENT);
        params.add(AGENCY_NUMBER_PARAM, AGENCY_ACCOUNT);
        params.add(ACCOUNT_NUMBER_PARAM, ACCOUNT_NUMBER);
        params.add(ACCOUNT_HOLDER_NAME_PARAM, ACCOUNT_HOLDER_NAME);
        params.add(PERSON_TYPE_PARAM, String.valueOf(PERSON_NATURAL_TYPE));

        when(pixAccountUserDetailsServiceMock.create(any())).thenReturn(createPixEmailAccountUserDetails());

        performPostSuccessfully(PIX_ACCOUNT_USER_DETAILS, createPixEmailAccountUserDetails(), params)
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdate() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(KEY_TYPE_PARAM, KEY_TYPE_EMAIL);
        params.add(KEY_VALUE_PARAM, KEY_VALUE_EMAIL);
        params.add(ACCOUNT_TYPE_PARAM, ACCOUNT_TYPE_CURRENT);
        params.add(AGENCY_NUMBER_PARAM, AGENCY_ACCOUNT);
        params.add(ACCOUNT_NUMBER_PARAM, ACCOUNT_NUMBER);
        params.add(ACCOUNT_HOLDER_NAME_PARAM, ACCOUNT_HOLDER_NAME);
        params.add(ACCOUNT_HOLDER_LAST_NAME_PARAM, HOLDER_LAST_NAME);
        params.add(PERSON_TYPE_PARAM, String.valueOf(PERSON_NATURAL_TYPE));

        PixAccountUserDetails pixEmailAccountUserDetails = createPixEmailAccountUserDetails();

        when(pixAccountUserDetailsServiceMock.findById(ID_FIND)).thenReturn(createPixEmailAccountUserDetails());
        when(pixAccountUserDetailsServiceMock.update(any())).thenReturn(createPixCpfAccountUserDetails());

        performPut(PIX_ACCOUNT_USER_DETAILS + "/{id}", pixEmailAccountUserDetails, params, ID_FIND)
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDelete() throws Exception {

        when(pixAccountUserDetailsServiceMock.findById(Long.parseLong(ID))).thenReturn(createPixEmailAccountUserDetails());

        performDeleteSuccessfully(PIX_ACCOUNT_USER_DETAILS + "/{id}", ID)
                .andExpect(status().isOk());
    }

}
