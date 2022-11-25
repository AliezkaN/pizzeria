package com.pizzamamamia.pizzeria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.service.IngredientService;
import com.pizzamamamia.pizzeria.service.OrderService;
import com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = IngredientController.class)
@AutoConfigureMockMvc
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IngredientService ingredientService;

    private static final String MAPPING = "/api/v1/ingredients/";

    @Test
    void getAllTest() throws Exception {
        IngredientDto expectedIngredient = TestIngredientDataUtil.createIngredientDto();
        when(ingredientService.getAll()).thenReturn(Collections.singletonList(expectedIngredient));

        mockMvc.perform(get(MAPPING))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(expectedIngredient.getName()));
    }

    @Test
    void getIngredientTest() throws Exception {
        IngredientDto expectedIngredient = TestIngredientDataUtil.createIngredientDto();
        when(ingredientService.getIngredient(MOCK_ID)).thenReturn(expectedIngredient);

        mockMvc.perform(get(MAPPING + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedIngredient.getName()));
    }

    @Test
    void createIngredientTest() throws Exception {
        IngredientDto createBody = TestIngredientDataUtil.createIngredientDto();
        IngredientDto expectedIngredient = TestIngredientDataUtil.createIngredientDto();
        when(ingredientService.createIngredient(createBody)).thenReturn(expectedIngredient);

        mockMvc.perform(
                        post(MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedIngredient.getName()));
    }

    @Test
    void updateIngredientTest() throws Exception {
        IngredientDto updateBody = TestIngredientDataUtil.createIngredientDto();
        IngredientDto expectedIngredient = TestIngredientDataUtil.createIngredientDto();
        when(ingredientService.updateIngredient(MOCK_ID,updateBody)).thenReturn(expectedIngredient);

        mockMvc.perform(
                        patch(MAPPING + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedIngredient.getName()));
    }

    @Test
    void deleteIngredientTest() throws Exception {
        doNothing().when(ingredientService).deleteIngredient(MOCK_ID);

        mockMvc
                .perform(delete(MAPPING + MOCK_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
