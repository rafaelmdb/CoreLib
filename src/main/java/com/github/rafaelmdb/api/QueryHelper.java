package com.github.rafaelmdb.api;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QueryHelper<Entity extends BaseEntity> {
    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int MAX_PAGE_SIZE = 1000;

    private MessageService messageService;

    public QueryHelper(MessageService messageService) {
        this.messageService = messageService;
    }

    public Page<Entity> obterPorExemplo(JpaRepository<Entity, UUID> repo, Entity filtro, Integer pageNo, Integer pageSize, String sortBy){
        if (pageNo==null || pageNo<=0){
            pageNo=1;
        }

        if(pageSize==null || pageSize<=0){
            pageSize=DEFAULT_PAGE_SIZE;
        }

        validarQuery(repo, filtro, pageNo, pageSize, sortBy);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example example = Example.of(filtro, matcher);

        Pageable pageable;

        if (sortBy!=null) {
            pageable = PageRequest.of(pageNo,
                    pageSize,
                    Sort.by(sortBy));
        }
        else{
            pageable = PageRequest.of(pageNo,
                    pageSize);
        }

        return repo.findAll(example, pageable);
    }

    private void validarQuery(JpaRepository<Entity, UUID> repo, Entity filtro, Integer pageNo, Integer pageSize, String sortBy) {
        messageService.validar(pageNo==null, "pagina.nao.informada");
        messageService.validar(pageSize==null, "pagina.invalida");
        messageService.validar(pageSize>MAX_PAGE_SIZE, "tamanho.pagina.excedido");
        messageService.validar(repo==null, "repositorio.nao.inicializado");
        messageService.validar(pageNo<0, "pagina.nao.informada");
        messageService.validar(pageSize<0, "pagina.invalida");
    }
}
