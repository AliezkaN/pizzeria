package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.exception.IngredientNotFoundException;
import com.pizzamamamia.pizzeria.exception.PizzaNotFoundException;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Pizza;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.repository.PizzaRepository;
import com.pizzamamamia.pizzeria.service.impl.PizzaServiceImpl;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestPizzaDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void getAllTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        List<Pizza> pizzaList = Collections.singletonList(expectedPizza);
        when(pizzaRepository.findAll()).thenReturn(pizzaList);

        //when
        List<PizzaDto> pizzas = pizzaService.getAll();

        //then
        assertThat(pizzas, hasSize(1));
    }

    @Test
    void getPizzaTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));

        //when
        PizzaDto actualPizza = pizzaService.getPizza(MOCK_ID);

        //then
        assertEquals(expectedPizza.getName(),actualPizza.getName());
        assertThat(actualPizza,allOf(
                hasProperty("name",equalTo(expectedPizza.getName())),
                hasProperty("ingredients",equalTo(expectedPizza.getIngredients()))
        ));
    }

    @Test
    void createPizzaTest(){
        //given
        PizzaDto createBody = TestPizzaDataUtil.createPizzaDto();
        System.out.println(createBody);
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        when(pizzaRepository.save(any())).thenReturn(expectedPizza);

        //when
        PizzaDto actualPizza = pizzaService.createPizza(createBody);

        //then
        assertEquals(expectedPizza.getName(),actualPizza.getName());
        assertThat(actualPizza,allOf(
                hasProperty("name",equalTo(expectedPizza.getName()))
        ));
    }

    @Test
    void updatePizzaTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        PizzaDto updateBody = PizzaDto.builder()
                .name(MOCK_UPDATE_NAME)
                .build();

        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        when(pizzaRepository.save(any())).thenReturn(expectedPizza);

        //when
        updateBody = pizzaService.updatePizza(MOCK_ID,updateBody);

        assertThat(updateBody, allOf(
                hasProperty("name",equalTo(expectedPizza.getName()))
        ));
    }

    @Test
    void DeletePizzaTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        doNothing().when(pizzaRepository).delete(any());

        //when
        pizzaService.deletePizza(MOCK_ID);

        //then
        verify(pizzaRepository, times(1)).delete(expectedPizza);
    }

    @Test
    void addIngredientTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        Ingredient ingredient = TestIngredientDataUtil.createIngredient();
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(ingredient));
        when(pizzaRepository.save(any())).thenReturn(expectedPizza);

        //when
        PizzaDto pizzaDto = pizzaService.addIngredient(MOCK_ID,MOCK_ID);

        //then
        List<IngredientDto> ingredientList = pizzaDto.getIngredients();
        assertThat(ingredientList,hasSize(1));
        assertThat(ingredientList.get(0),allOf(
                hasProperty("name",equalTo(ingredient.getName())),
                hasProperty("price",equalTo(ingredient.getPrice()))
        ));
    }

    @Test
    void deleteIngredientTest(){
        //given
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        Ingredient ingredient = TestIngredientDataUtil.createIngredient();
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(ingredient));
        when(pizzaRepository.save(any())).thenReturn(expectedPizza);

        //when
        PizzaDto pizzaDto = pizzaService.deleteIngredient(MOCK_ID,MOCK_ID);

        //then
        assertThat(pizzaDto.getIngredients(), hasSize(0));
    }

    @Test
    void getPizzaNotFoundTest(){
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(PizzaNotFoundException.class, () -> pizzaService.getPizza(MOCK_ID));
    }

    @Test
    void updatePizzaNotFoundTest(){
        PizzaDto updateBody = TestPizzaDataUtil.createPizzaDto();
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(PizzaNotFoundException.class, () -> pizzaService.updatePizza(MOCK_ID,updateBody));
    }

    @Test
    void deletePizzaNotFoundTest(){
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(PizzaNotFoundException.class, () -> pizzaService.deletePizza(MOCK_ID));
    }
}
