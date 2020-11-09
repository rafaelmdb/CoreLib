package com.github.rafaelmdb.converters;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.base.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<Entity extends BaseEntity, Dto extends BaseDto> {
    abstract protected Entity DoCreateFrom(Dto dto);
    abstract protected Dto DoCreateFrom(Entity entity);

    public Entity createFrom(Dto dto){
        if (dto==null){
            throw new RuntimeException("Objeto nulo! " + this.toString());
        }

        return DoCreateFrom(dto);
    }

    public Dto createFrom(Entity entity){
        if (entity ==null){
            throw new RuntimeException("Objeto nulo! " + this.toString());
        }

        return DoCreateFrom(entity);
    }

     public List<Dto> createFromEntities(final Collection<Entity> entities) {
        return entities.stream()
                .map(this::createFrom)
                .collect(Collectors.toList());
    }

     public List<Entity> createFromDtos(final Collection<Dto> dtos) {
        return dtos.stream()
                .map(this::createFrom)
                .collect(Collectors.toList());
    }
}