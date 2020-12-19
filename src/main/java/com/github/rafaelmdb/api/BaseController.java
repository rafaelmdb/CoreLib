package com.github.rafaelmdb.api;

import com.github.rafaelmdb.converters.BaseConverter;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.data.domain.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BaseController {

    private static final String PAGE_NO = "PAGE_NO";
    private static final String PAGE_SIZE = "PAGE_SIZE";
    private static final String LIMIT = "LIMIT";
    private final MessageService messageService;

    public BaseController(MessageService messageService){

        this.messageService = messageService;
    }

    protected List prepararRetornoListaPaginada(Page page, BaseConverter converter, HttpServletResponse response){
        messageService.validar(page==null, "pagina.nao.inicializada");
        messageService.validar(converter==null, "converter.nao.inicializado");
        messageService.validar(response==null, "servletresponse.nao.inicializado");

        response.setHeader(PAGE_NO, String.valueOf(page.getPageable().getPageNumber()));
        response.setHeader(PAGE_SIZE, String.valueOf(page.getPageable().getPageSize()));
        response.setHeader(LIMIT, String.valueOf(page.getTotalPages()>page.getPageable().getPageNumber()+1));

        return converter.createFromEntities(page.getContent());
    }

    public MessageService getMessageService(){
        return this.messageService;
    }
}
