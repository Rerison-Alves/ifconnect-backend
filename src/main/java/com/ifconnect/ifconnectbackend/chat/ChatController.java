package com.ifconnect.ifconnectbackend.chat;

import com.ifconnect.ifconnectbackend.encontro.EncontroService;
import com.ifconnect.ifconnectbackend.mensagem.MensagemRepository;
import com.ifconnect.ifconnectbackend.models.Encontro;
import com.ifconnect.ifconnectbackend.models.Mensagem;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UsuarioService usuarioService;
    private final EncontroService encontroService;
    private final MensagemRepository mensagemRepository;

    @MessageMapping("/new-message/{encontroId}")
    @SendTo("/topics/live-chat/{encontroId}")
    public Mensagem newMessage(@DestinationVariable Integer encontroId, Mensagem message) {
        Usuario usuario = usuarioService.findById(message.getUsuario().getId());
        Encontro encontro = encontroService.findById(encontroId);
        message.setData(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        message.setUsuario(usuario);
        message.setEncontro(encontro);
        return mensagemRepository.saveAndFlush(message);
    }
}