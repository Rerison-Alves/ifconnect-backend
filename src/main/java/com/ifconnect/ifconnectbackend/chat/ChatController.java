package com.ifconnect.ifconnectbackend.chat;

import com.ifconnect.ifconnectbackend.mensagem.MensagemService;
import com.ifconnect.ifconnectbackend.models.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MensagemService service;

    @MessageMapping("/sendMessage/encontros/{encontroId}")
    @SendTo("/topic/encontros/{encontroId}/chat")
    public Mensagem sendMessage(@DestinationVariable Integer encontroId, @Payload Mensagem message) {
        return service.saveOrUpdate(message);
    }

    @MessageMapping("/addUser/encontros/{encontroId}")
    @SendTo("/topic/encontros/{encontroId}/chat")
    public Mensagem addUser(@DestinationVariable Integer encontroId, @Payload Mensagem message, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if(sessionAttributes!=null){
            sessionAttributes.put("usuario", message.getUsuario());
            sessionAttributes.put("encontro", message.getEncontro());
        }
        return message;
    }
}