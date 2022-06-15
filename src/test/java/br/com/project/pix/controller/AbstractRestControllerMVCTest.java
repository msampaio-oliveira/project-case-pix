package br.com.project.pix.controller;

import br.com.project.pix.helper.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AbstractRestControllerMVCTest {

  protected static final String EXCEPTION_RESP_CODE_FIELD = "code";
  protected static final String EXCEPTION_RESP_STATUS_FIELD = "status";
  protected static final String EXCEPTION_RESP_DESC_FIELD = "description";
  protected static final String EXCEPTION_RESP_DATE_FIELD = "date";
  protected static final String EXCEPTION_RESP_ATTR_FIELD = "attributes";

  private ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  public void setup() throws Exception {
    this.mapper = new ObjectMapper();
    this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  protected ResultActions performPostSuccessfully(String path, Object request,
      MultiValueMap<String, String> requestParams, Object... pathParams) throws Exception {
    return mockMvc //
        .perform(post(path, pathParams) //
            .contentType(APPLICATION_JSON) //
            .content(json(request)) //
            .params(requestParams));
  }

  protected ResultActions performPatchSuccessfully(String path, Object request,
      MultiValueMap<String, String> requestParams, Object... pathParams) throws Exception {
    return mockMvc //
        .perform(patch(path, pathParams) //
            .contentType(APPLICATION_JSON) //
            .content(json(request)) //
            .params(requestParams)) //
        .andExpect(status().isNoContent());
  }

  protected ResultActions performPut(String path, Object request,
      MultiValueMap<String, String> requestParams, Object... pathParams) throws Exception {
    return mockMvc //
        .perform(put(path, pathParams) //
            .contentType(APPLICATION_JSON) //
            .content(json(request)) //
            .params(requestParams));
  }

  protected ResultActions performPatchWithError(String path, HttpStatus error,
      MultiValueMap<String, String> requestParams, Object request, Object... pathParams) throws Exception {
    assertFalse("Invalid param, this method only accept HTTP errors!", error.is2xxSuccessful());
    return mockMvc //
        .perform(patch(path, pathParams) //
            .contentType(APPLICATION_JSON) //
            .content(json(request)) //
            .params(requestParams)) //
        .andExpect(status().is(error.value())).andExpect(jsonPath(EXCEPTION_RESP_CODE_FIELD, is(error.value())))
        .andExpect(jsonPath(EXCEPTION_RESP_STATUS_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_DESC_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_DATE_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_ATTR_FIELD, notNullValue()));
  }

  protected ResultActions performPostWithError(String path, HttpStatus error,
      MultiValueMap<String, String> requestParams, Object request, Object... pathParams) throws Exception {
    assertFalse("Invalid param, this method only accept HTTP errors!", error.is2xxSuccessful());
    return mockMvc //
        .perform(post(path, pathParams) //
            .contentType(APPLICATION_JSON) //
            .content(json(request)) //
            .params(requestParams)) //
        .andExpect(status().is(error.value())).andExpect(jsonPath(EXCEPTION_RESP_CODE_FIELD, is(error.value())))
        .andExpect(jsonPath(EXCEPTION_RESP_STATUS_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_DESC_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_DATE_FIELD, notNullValue()))
        .andExpect(jsonPath(EXCEPTION_RESP_ATTR_FIELD, notNullValue()));
  }

  protected ResultActions performDeleteSuccessfully(String path, Object... pathParams) throws Exception {

    return mockMvc //
        .perform(delete(path, pathParams) //
            .contentType(APPLICATION_JSON));
  }

  protected ResultActions performGetSuccessfully(String path, Object... pathParams) throws Exception {
    return mockMvc //
        .perform(get(path, pathParams) //
            .contentType(APPLICATION_JSON));
  }

  protected String json(Object o) throws IOException {
    return this.mapper.writeValueAsString(o);
  }

  @Test
  public void shouldReturnJson() throws IOException {
    this.mapper = new ObjectMapper();
    this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    assertNotNull("Should not be null!", json(Helper.createPixEmailAccountUserDetails()));
  }

}