package com.github.rafaelmdb.api;

import com.github.rafaelmdb.exception.RegraNegocioException;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    /***
     * Regra de negócio foi quebrada
     * @param exception
     * @return
     */
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception) {
        String mensagem = exception.getMessage();
        return new ApiErrors(mensagem);
    }

    /***x
     * Json enviado é inválido
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleInvalidJson(HttpMessageNotReadableException exception) {
        return new ApiErrors("Valor inválido recebido");
    }

    /***
     * Método chamado não é válido. Não existe.
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

    /****
     * Argumento inválido enviado na URL. Por exemplo: UUID inválido numa requisição GET
     * @param exception
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleIllegalArgumentException(HttpMessageNotReadableException exception) {
        return new ApiErrors("Argumento inválido recebido");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrors handleIllegalArgumentException(ConstraintViolationException exception) {
        List<String> mensagens =
                exception.getConstraintViolations()
                .stream()
                .map(constraintViolation->constraintViolation.getMessage())
                .collect(Collectors.toList());

        String mensagem = StringUtils.join(mensagens, '\n');
        return new ApiErrors(mensagem);
    }
}
