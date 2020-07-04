package com.troublemakersacademy.messageservice.config;

import com.troublemakersacademy.messageservice.domain.MessageRequest;
import com.troublemakersacademy.messageservice.domain.MessageResponse;
import com.troublemakersacademy.messageservice.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class MessageConfig {

    @Bean
    RouterFunction<ServerResponse>  routes(MessageService messageService){
        return route()
                .GET("/message/{message}", request -> ok()
                        .body(messageService.getMessageReplyOnce(new MessageRequest(request.pathVariable("message"))), MessageResponse.class))
                .GET("/messages/{message}", request -> ok().contentType(TEXT_EVENT_STREAM)
                .body(messageService.getMessageReplyMany(new MessageRequest(request.pathVariable("message"))), MessageResponse.class)).build();
    }
}
