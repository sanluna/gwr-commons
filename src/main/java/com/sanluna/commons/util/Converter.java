package com.sanluna.commons.util;

import com.sanluna.commons.model.BaseDTO;
import com.sanluna.commons.model.entity.BaseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Converter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatTime(LocalDateTime time){
        if(time == null)return null;
        return time.format(formatter);
    }

    public static LocalDateTime formatTime(String time){
        if(time == null)return null;
        return LocalDateTime.parse(time, formatter);
    }

    /**
     *
     * Handles the basic conversion of every entity into an dto (the basic information put on every entity)
     */
    public static <TDTO extends BaseDTO<TDTO>, TEntity extends BaseEntity<TEntity>> TDTO toDTO(TEntity entity, TDTO dto) {
        return dto.setActive(entity.isActive())
                .setId(entity.getId().toString())
                .setCreatedBy(entity.getCreatedBy())
                .setCreated(formatTime(entity.getCreated()))
                .setHidden(entity.isHidden())
                .setLastModified(formatTime(entity.getLastModified()))
                .setLastModifiedBy(entity.getLastModifiedBy());
    }

    public static <TDTO extends BaseDTO<TDTO>, TEntity extends BaseEntity<TEntity>> TEntity toEntity(TDTO dto, TEntity entity) {
        if (dto.getId() != null) {
            entity.setId(UUID.fromString(dto.getId()));
        }
        return entity.setActive(dto.isActive())
                .setCreatedBy(dto.getCreatedBy())
                .setCreated(formatTime(dto.getCreated()))
                .setHidden(dto.isHidden())
                .setLastModified(formatTime(dto.getLastModified()))
                .setLastModifiedBy(dto.getLastModifiedBy());
    }
}
