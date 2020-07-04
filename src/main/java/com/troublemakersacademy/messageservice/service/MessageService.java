package com.troublemakersacademy.messageservice.service;

import com.troublemakersacademy.messageservice.domain.MessageRequest;
import com.troublemakersacademy.messageservice.domain.MessageResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class MessageService {

   public Mono<MessageResponse>  getMessageReplyOnce(MessageRequest messageRequest){
        return Mono.just(getMessageResponse(messageRequest.getMessage()));
    }

    public Flux<MessageResponse> getMessageReplyMany(MessageRequest messageRequest){
        return     Flux.fromStream(  Stream.generate(new Supplier<MessageResponse>() {

            @Override
            public MessageResponse get() {
                return getMessageResponse(messageRequest.getMessage());
            }
        })).delayElements(Duration.ofSeconds(1));
    }

    private  MessageResponse getMessageResponse(String message){
        return new MessageResponse("HI,your message was received @" + Instant.now() +" message="+ message);
    }
}
