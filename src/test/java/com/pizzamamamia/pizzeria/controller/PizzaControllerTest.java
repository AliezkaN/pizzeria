package com.pizzamamamia.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.service.PizzaService;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestPizzaDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.MOCK_ID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PizzaController.class)
@AutoConfigureMockMvc
public class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PizzaService pizzaService;

    private static final String MAPPING = "/api/v1/pizza/";

    @Test
    void getAllTest() throws Exception {
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        when(pizzaService.getAll()).thenReturn(Collections.singletonList(expectedPizza));

        mockMvc.perform(get(MAPPING))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(expectedPizza.getName()));
    }

    @Test
    void getPizzaTest() throws Exception {
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        when(pizzaService.getPizza(MOCK_ID)).thenReturn(expectedPizza);

        mockMvc.perform(get(MAPPING + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedPizza.getName()));
    }

    @Test
    void createPizzaTest() throws Exception {
        PizzaDto createBody = TestPizzaDataUtil.createPizzaDto();
        createBody.setIngredients(null);
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        when(pizzaService.createPizza(createBody)).thenReturn(expectedPizza);

        mockMvc.perform(
                        post(MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedPizza.getName()));
    }

    @Test
    void updatePizzaTest() throws Exception {
        PizzaDto updateBody = TestPizzaDataUtil.createPizzaDto();
        updateBody.setIngredients(null);
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        when(pizzaService.updatePizza(MOCK_ID,updateBody)).thenReturn(expectedPizza);

        mockMvc.perform(
                        patch(MAPPING + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedPizza.getName()));
    }

    @Test
    void deletePizzaTest() throws Exception {
        doNothing().when(pizzaService).deletePizza(MOCK_ID);

        mockMvc
                .perform(delete(MAPPING + MOCK_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void addIngredientToPizzaTest() throws Exception {
        PizzaDto emptyPizza = TestPizzaDataUtil.createPizzaDto();
        emptyPizza.getIngredients().clear();
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        expectedPizza.getIngredients().add(TestIngredientDataUtil.createIngredientDto());
        when(pizzaService.addIngredient(MOCK_ID,MOCK_ID)).thenReturn(expectedPizza);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/addIngredient/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedPizza.getName()))
                .andExpect(jsonPath("$.ingredients.size()").value(1));

    }

    @Test
    void deleteIngredientFromPizzaTest() throws Exception {
        PizzaDto emptyPizza = TestPizzaDataUtil.createPizzaDto();
        emptyPizza.getIngredients().clear();
        PizzaDto expectedPizza = TestPizzaDataUtil.createPizzaDto();
        when(pizzaService.deleteIngredient(MOCK_ID,MOCK_ID)).thenReturn(emptyPizza);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/deleteIngredient/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedPizza.getName()))
                .andExpect(jsonPath("$.ingredients.size()").value(0));
    }
}
