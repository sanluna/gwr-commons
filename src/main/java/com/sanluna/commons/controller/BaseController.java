package com.sanluna.commons.controller;

import com.sanluna.commons.model.BaseDTO;

import java.util.List;

public interface BaseController<T extends BaseDTO<T>> {

    T save(T dto);

    List<T> fetchAll();

    T getById(String ID);

    void delete(T dto);

    void deleteById(String ID);

}
