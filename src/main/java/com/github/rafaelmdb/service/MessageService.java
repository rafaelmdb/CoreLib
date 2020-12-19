package com.github.rafaelmdb.service;

public interface MessageService {
    String getMessage(String message, Object[] objects);
    void validar(Boolean throwsIfTrue, String message);

}
