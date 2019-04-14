package com.sanluna.commons.service;

import com.sanluna.commons.exceptions.AlreadyExistsException;
import com.sanluna.commons.exceptions.NotFoundException;
import com.sanluna.commons.model.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import com.sanluna.commons.repository.AOWR_Repository;

import java.util.List;
import java.util.UUID;

public abstract class GenericService<T extends BaseEntity<T>> implements BaseService<T> {

    @Autowired
    private AOWR_Repository<T> genericRepository;

    @Override
    public T save(T entity) {
        try {
            checkIfFound(entity.getId().toString());
        } catch (NotFoundException | NullPointerException e) {
            try {
                return genericRepository.save(entity.createNew());
            } catch (DataIntegrityViolationException e1) {
                throw new AlreadyExistsException("object already exists!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        throw new AlreadyExistsException("object with id " + entity.getId().toString() + " already exists!");
    }

    @Override
    public List<T> fetchAll() {
        List<T> entities = genericRepository.findAll();
        if (entities.isEmpty()) {
            throw new NotFoundException("Results returned empty");
        }
        return entities;
    }

    @Override
    public T getById(String ID) {
        return checkIfFound(ID);
    }

    @Override
    public void delete(T entity) {
        checkIfFound(entity.getId().toString());
        genericRepository.delete(entity);
    }

    @Override
    public void deleteById(String ID) {
        checkIfFound(ID);
        genericRepository.deleteById(UUID.fromString(ID));
    }

    protected T checkIfFound(String ID) {
        T entity = genericRepository.findById(UUID.fromString(ID));
        if (entity == null)
            throw new NotFoundException("No object with id " + ID + " found");
        else return entity;
    }

    protected T checkIfFound(T entity, String message) {
        if (entity == null)
            throw new NotFoundException(message);
        else return entity;
    }

}
