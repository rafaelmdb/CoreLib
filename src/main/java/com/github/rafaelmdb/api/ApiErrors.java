package com.github.rafaelmdb.api;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String mensagem){

        this.errors = Arrays.asList(mensagem);
    }

    public ApiErrors(List<String> mensagens) {
        this.errors = mensagens;
    }
}
