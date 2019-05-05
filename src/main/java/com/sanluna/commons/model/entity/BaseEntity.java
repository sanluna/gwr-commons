package com.sanluna.commons.model.entity;

import com.sanluna.commons.model.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {

    @Id
    @Column(columnDefinition = "binary(16)", unique = true)
    private UUID id;
    @Column(name = "active")
    private boolean active;
    @Column(name = "hidden")
    private boolean hidden;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public T createNew(String username) {
        if (this.id == null) {
            this.id = UUID.randomUUID();
            this.active = true;
            this.hidden = false;
            this.created = LocalDateTime.now();
            this.createdBy = username;
        }
        return (T) this;
    }

    public T createNew() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
            this.active = true;
            this.hidden = false;
            this.created = LocalDateTime.now();
            this.createdBy = "System";
        }
        return (T) this;
    }

    public UUID getId() {
        return id;
    }

    public T setId(UUID id) {
        this.id = id;
        return (T) this;
    }

    public boolean isActive() {
        return active;
    }

    public T setActive(boolean active) {
        this.active = active;
        return (T) this;
    }

    public boolean isHidden() {
        return hidden;
    }

    public T setHidden(boolean hidden) {
        this.hidden = hidden;
        return (T) this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public T setCreated(LocalDateTime created) {
        this.created = created;
        return (T) this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public T setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return (T) this;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public T setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
        return (T) this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public T setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public abstract <T1 extends BaseDTO<T1>> T1 convertToDTO();

    @SuppressWarnings("unchecked")
    public abstract T updateEntity(T newEntity);

}
