package com.sanluna.commons.model;

import com.sanluna.commons.model.entity.BaseEntity;

public abstract class BaseDTO<T extends BaseDTO<T>> {

    private String id;
    private boolean active;
    private boolean hidden;
    private String created;
    private String createdBy;
    private String lastModified;
    private String lastModifiedBy;

    public String getId() {
        return id;
    }

    public T setId(String id) {
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

    public String getCreated() {
        return created;
    }

    public T setCreated(String created) {
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

    public String getLastModified() {
        return lastModified;
    }

    public T setLastModified(String lastModified) {
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
    public abstract <T1 extends BaseEntity<T1>> T1 convertToEntity();

}
