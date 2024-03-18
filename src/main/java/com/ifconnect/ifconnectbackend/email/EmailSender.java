package com.ifconnect.ifconnectbackend.email;

public interface EmailSender {
    void send(String to, String email);

    String confirmEmail(String name, String link);

    String confirmedPage();

    String erroConfirmPage(String message);
}