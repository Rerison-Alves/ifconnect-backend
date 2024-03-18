package com.ifconnect.ifconnectbackend.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // Regex para validar o formato do email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Regex para verificar se pertence ao domínio ifce.edu.br ou subdomínios
        String domainRegex = "(?:[a-zA-Z0-9-]+\\.)*ifce\\.edu\\.br$";

        // Compilar os padrões regex
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern domainPattern = Pattern.compile(domainRegex);

        // Verificar se o email está no formato correto
        Matcher emailMatcher = emailPattern.matcher(s);
        if (!emailMatcher.matches()) {
            return false;
        }

        // Extrair o domínio do email
        String[] parts = s.split("@");
        String domain = parts[1];

        // Verificar se o domínio é ifce.edu.br ou subdomínios
        Matcher domainMatcher = domainPattern.matcher(domain);
        return domainMatcher.find();
    }
}