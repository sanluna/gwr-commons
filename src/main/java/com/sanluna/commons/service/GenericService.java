package com.sanluna.commons.service;

import com.sanluna.commons.exceptions.AlreadyExistsException;
import com.sanluna.commons.exceptions.NotFoundException;
import com.sanluna.commons.model.entity.BaseEntity;
import com.sanluna.commons.repository.AOWR_Repository;
import com.sanluna.security.GWRTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.time.LocalDateTime;
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
                return genericRepository.save(entity.createNew(GWRTokenHelper.currentUsername()));
            } catch (DataIntegrityViolationException e1) {
                throw new AlreadyExistsException("object already exists!");
            } catch (Exception e2) {
                e2.printStackTrace();
                return genericRepository.save(entity.createNew());
            }
        } catch (Exception e) {
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
    public T update(T newEntity) {
        T oldEntity = checkIfFound(newEntity.getId().toString());
        oldEntity.updateEntity(newEntity);
        oldEntity.setLastModified(LocalDateTime.now());
        try {
            oldEntity.setLastModifiedBy(GWRTokenHelper.currentUsername());
        } catch (AuthenticationCredentialsNotFoundException e2) {
            oldEntity.setLastModifiedBy("System");
        }
        return genericRepository.save(oldEntity);
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
