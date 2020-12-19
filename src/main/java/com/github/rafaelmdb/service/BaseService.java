package com.github.rafaelmdb.service;

import com.github.rafaelmdb.exception.RegraNegocioException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public class BaseService {
    private MessageService messageService;

    public BaseService(MessageService messageService){
        this.messageService = messageService;
    }

    protected void validarAlteracao(UUID id, JpaRepository repo) {
        messageService.validar(id!=null, "id.nao.informado");
        messageService.validar(repo.existsById(id),"registro.nao.encontrado");
    }

    public MessageService getMessageService(){
        return messageService;
    }

}
