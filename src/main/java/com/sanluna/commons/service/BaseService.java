package com.sanluna.commons.service;

import com.sanluna.commons.model.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T extends BaseEntity<T>> {

    T save(T entity);

    List<T> fetchAll();

    T getById(String ID);

    void delete(T entity);

    void deleteById(String ID);

}
