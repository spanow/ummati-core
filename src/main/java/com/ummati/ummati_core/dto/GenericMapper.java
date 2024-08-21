package com.ummati.ummati_core.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <D, T> D toDTO(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, T> T toEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <D, T> List<D> toDTOList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream().map(entity -> toDTO(entity, dtoClass)).collect(Collectors.toList());
    }

    public <D, T> List<T> toEntityList(List<D> dtoList, Class<T> entityClass) {
        return dtoList.stream().map(dto -> toEntity(dto, entityClass)).collect(Collectors.toList());
    }
}
