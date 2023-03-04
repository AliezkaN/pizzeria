package com.pizzamamamia.pizzeria.service.mappers;

public interface Mapper <T,V>{
    V toDto(T domain);
    T toDomain(V dto);
}
