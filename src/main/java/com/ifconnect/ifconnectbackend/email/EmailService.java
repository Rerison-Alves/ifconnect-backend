package com.ifconnect.ifconnectbackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String email, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("noresponse@ifconnect.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    public String confirmEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirme seu email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Olá " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Obrigado por se registrar no sistema do If-Connect. Por favor clique no link abaixo para ativar sua conta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Ative agora</a> </p></blockquote>\n O link irá expirar em 15 minutos. <p>Até logo!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public String codeEmail(String name, int value){
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "    <span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "    <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "\n" +
                "                    <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                                    <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                                        <tbody>\n" +
                "                                            <tr>\n" +
                "                                                <td style=\"padding-left:10px\">\n" +
                "\n" +
                "                                                </td>\n" +
                "                                                <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                                                    <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Alteração de senha</span>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </tbody>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "\n" +
                "    <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "                <td>\n" +
                "\n" +
                "                    <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                </td>\n" +
                "                <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td height=\"30\"><br></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "                <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Olá "+name+",</p>\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Recebos uma solicitação de alteração de senha, se não foi você desconsiderar essse email. </p>\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Seu código de verificação é: <strong>"+value+"</strong></p>\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Por favor, insira este código para validar sua conta.</p>\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">O código irá expirar em 15 minutos.</p>\n" +
                "                    <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Até logo!</p>\n" +
                "\n" +
                "                </td>\n" +
                "                <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td height=\"30\"><br></td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "    <div class=\"yj6qo\"></div>\n" +
                "    <div class=\"adL\"></div>\n" +
                "</div>\n";
    }

    public String confirmedPage(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Confirmado</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 16px;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            color: #0b0c0c;\n" +
                "            background-image: url('https://svgur.com/i/14Vc.svg');\n" +
                "            background-size: cover;\n" +
                "            background-position: center;\n" +
                "            background-repeat: no-repeat;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        .top-bar {\n" +
                "            background-color: #0b0c0c;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 0;\n" +
                "            text-align: center;\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            left: 0;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-size: 28px;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-size: 19px;\n" +
                "            line-height: 1.6;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #1D70B8;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"top-bar\">\n" +
                "        <h2>IF-Connect</h2>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Email Confirmado com Sucesso</h1>\n" +
                "        <p>Obrigado por confirmar seu email no sistema do if-connect. Sua conta agora está ativa.</p>\n" +
                "        <p>Faça login no aplicativo para usar o sistema</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }

    public String erroConfirmPage(String message){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Falha ao confirmar email</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 16px;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            color: #0b0c0c;\n" +
                "            background-image: url('https://svgur.com/i/14Vc.svg');\n" +
                "            background-size: cover;\n" +
                "            background-position: center;\n" +
                "            background-repeat: no-repeat;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        .top-bar {\n" +
                "            background-color: #0b0c0c;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 0;\n" +
                "            text-align: center;\n" +
                "            position: absolute;\n" +
                "            top: 0;\n" +
                "            left: 0;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-size: 28px;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-size: 19px;\n" +
                "            line-height: 1.6;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #1D70B8;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"top-bar\">\n" +
                "        <h2>IF-Connect</h2>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Não foi possível confirmar o Email</h1>\n" +
                "        <p>"+ message +"</p>\n" +
                "        <p>Se estiver tendo problemas contacte o suporte!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }
}