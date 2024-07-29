package com.ifconnect.ifconnectbackend.config;

import com.ifconnect.ifconnectbackend.models.Encontro;
import com.ifconnect.ifconnectbackend.models.Mensagem;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.models.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Usuario usuario = (Usuario) headerAccessor.getSessionAttributes().get("usuario");
        Encontro encontro = (Encontro) headerAccessor.getSessionAttributes().get("encontro");
        if(usuario != null && encontro != null){
            var mensagem = Mensagem.builder()
                    .messageType(MessageType.LEAVER)
                    .usuario(usuario)
                    .encontro(encontro)
                    .build();
            String topic = String.format("/topic/encontros/%s/chat", mensagem.getEncontro().getId());
            messageTemplate.convertAndSend(topic, mensagem);
        }
    }
}
