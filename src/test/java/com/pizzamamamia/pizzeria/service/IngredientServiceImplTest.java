package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.exception.CustomerNotFoundException;
import com.pizzamamamia.pizzeria.exception.IngredientNotFoundException;
import com.pizzamamamia.pizzeria.model.Customer;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.service.impl.IngredientServiceImpl;
import com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.*;
import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.MOCK_UPDATE_ADDRESS;
import static com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil.*;
import static com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil.MOCK_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void getAllTest(){
        //given
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        List<Ingredient> ingredientList = Collections.singletonList(expectedIngredient);
        when(ingredientRepository.findAll()).thenReturn(ingredientList);

        //when
        List<IngredientDto> ingredients = ingredientService.getAll();

        //then
        assertThat(ingredients, hasSize(1));
    }

    @Test
    void getIngredientTest(){
        //given
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));

        //when
        IngredientDto actualIngredient = ingredientService.getIngredient(MOCK_ID);

        //then
        assertEquals(expectedIngredient.getName(),actualIngredient.getName());
        assertThat(actualIngredient,allOf(
                hasProperty("name",equalTo(expectedIngredient.getName())),
                hasProperty("price",equalTo(expectedIngredient.getPrice()))
        ));
    }

    @Test
    void createIngredientTest(){
        //given
        IngredientDto createBody = TestIngredientDataUtil.createIngredientDto();
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(ingredientRepository.save(any())).thenReturn(expectedIngredient);

        //when
        createBody = ingredientService.createIngredient(createBody);

        //then
        assertThat(createBody,allOf(
                hasProperty("name",equalTo(expectedIngredient.getName())),
                hasProperty("price",equalTo(expectedIngredient.getPrice()))
        ));
    }

    @Test
    void deleteIngredientTest(){
        //given
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        doNothing().when(ingredientRepository).delete(any());

        //when
        ingredientService.deleteIngredient(MOCK_ID);

        //then
        verify(ingredientRepository, times(1)).delete(expectedIngredient);
    }

    @Test
    void updateIngredientTest(){
        //given
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        IngredientDto updateBody = IngredientDto.builder()
                                                .name(MOCK_UPDATE_NAME)
                                                .price(MOCK_UPDATE_PRICE)
                                                .build();
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        when(ingredientRepository.save(any())).thenReturn(expectedIngredient);

        //when
        updateBody = ingredientService.updateIngredient(MOCK_ID,updateBody);

        assertThat(updateBody, allOf(
                hasProperty("name",equalTo(expectedIngredient.getName())),
                hasProperty("price",equalTo(expectedIngredient.getPrice()))
        ));
    }

    @Test
    void getIngredientNotFoundTest(){
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(IngredientNotFoundException.class, () -> ingredientService.getIngredient(MOCK_ID));
    }

    @Test
    void updateIngredientNotFoundTest(){
        IngredientDto updateBody = TestIngredientDataUtil.createIngredientDto();
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(IngredientNotFoundException.class, () -> ingredientService.updateIngredient(MOCK_ID,updateBody));
    }

    @Test
    void deleteIngredientNotFoundTest(){
            when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.empty());
            assertThrows(IngredientNotFoundException.class, () -> ingredientService.deleteIngredient(MOCK_ID));
    }
}
