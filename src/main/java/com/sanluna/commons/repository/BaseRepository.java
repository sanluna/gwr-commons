package com.sanluna.commons.repository;

import com.sanluna.commons.model.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<T>, K> extends Repository<T, K> {

    T save(T entity);

    List<T> findAll();

    T findById(K ID);

    void delete(T entity);

    void deleteById(K ID);

}