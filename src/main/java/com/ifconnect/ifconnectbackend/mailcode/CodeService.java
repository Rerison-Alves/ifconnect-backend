package com.ifconnect.ifconnectbackend.mailcode;

import com.ifconnect.ifconnectbackend.email.EmailSender;
import com.ifconnect.ifconnectbackend.models.Usuario;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@EnableAsync
public class CodeService {

    private final CodeRepository codeRepository;
    private final EmailSender emailSender;
    private final Random random = new Random();

    public Code find(int value, Usuario usuario){
        return codeRepository.findByValueAndUsuario(value,usuario).orElseThrow(() -> {
            throw new NoResultException("Código inválido");
        });
    }

    public void save(Code code){
        codeRepository.saveAndFlush(code);
    }

    public void generateAndSendCode(Usuario usuario) {

        // Gerar código aleatório de 5 dígitos
        int codeValue = generateRandomCode();

        // Criar novo objeto Code
        Code code = new Code();
        code.setValue(codeValue);
        code.setExpired(false);
        code.setUsuario(usuario);
        code.setCreatedAt(LocalDateTime.now());

        // Salvar o código no banco de dados
        codeRepository.save(code);
        code = codeRepository.findByValueAndUsuario(codeValue, usuario).orElse(null);
        if(code!=null){
            // Enviar o código por e-mail ao usuário
            emailSender.send(
                    usuario.getEmail(),
                    emailSender.codeEmail(usuario.getNome(), codeValue),
                    "Código de verificação");

        }

    }

    private int generateRandomCode() {
        return 10000 + random.nextInt(90000); // Garante que o código tenha 5 dígitos
    }

    @Scheduled(fixedDelay = 10000) // executar a cada 10 segundos
    private void scheduleCodeExpiration() {
        List<Code> codes = codeRepository.findByExpiredFalse();
        LocalDateTime now = LocalDateTime.now();
        for (Code code : codes) {
            LocalDateTime expirationTime = code.getCreatedAt().plusMinutes(15);
            if (now.isAfter(expirationTime)) {
                code.setExpired(true);
                codeRepository.save(code);
            }
        }
    }

}