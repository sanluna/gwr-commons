package com.sanluna.commons.repository;

import com.sanluna.commons.model.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface AOWR_Repository<T extends BaseEntity<T>> extends BaseRepository<T, UUID> {
}
