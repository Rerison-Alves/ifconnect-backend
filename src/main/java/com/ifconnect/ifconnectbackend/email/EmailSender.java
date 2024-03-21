package com.ifconnect.ifconnectbackend.email;

public interface EmailSender {
    void send(String to, String email, String subject);

    String confirmEmail(String name, String link);

    String codeEmail(String name, int value);

    String confirmedPage();

    String erroConfirmPage(String message);
}