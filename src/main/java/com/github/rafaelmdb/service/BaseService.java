package com.github.rafaelmdb.service;

import com.github.rafaelmdb.base.BaseDto;
import com.github.rafaelmdb.base.BaseEntity;
import com.github.rafaelmdb.exception.RegraNegocioException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class BaseService {
    protected void validarAlteracao(UUID id, JpaRepository repo) {
        if (id == null) {
            throw new RegraNegocioException("Id não foi informado");
        }

        if (!repo.existsById(id)) {
            throw new RegraNegocioException("Registro não encontrado");
        }
    }
}
