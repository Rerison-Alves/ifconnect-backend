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

    private static final Logger LOGGER = LoggerFactory
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
        return """
        <div style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c">

          <span style="display:none;font-size:1px;color:#fff;max-height:0"></span>

          <table role="presentation" width="100%%" style="border-collapse:collapse;min-width:100%%;width:100%%!important" cellpadding="0" cellspacing="0" border="0">
            <tbody><tr>
              <td width="100%%" height="53" bgcolor="#0b0c0c">

                <table role="presentation" width="100%%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                  <tbody><tr>
                    <td width="70" bgcolor="#0b0c0c" valign="middle">
                        <table role="presentation" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                          <tbody><tr>
                            <td style="padding-left:10px">

                            </td>
                            <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px">
                              <span style="font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block">Confirme seu email</span>
                            </td>
                          </tr>
                        </tbody></table>
                      </a>
                    </td>
                  </tr>
                </tbody></table>

              </td>
            </tr>
          </tbody></table>
          <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%%!important" width="100%%">
            <tbody><tr>
              <td width="10" height="10" valign="middle"></td>
              <td>

                      <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                        <tbody><tr>
                          <td bgcolor="#1D70B8" width="100%%" height="10"></td>
                        </tr>
                      </tbody></table>

              </td>
              <td width="10" valign="middle" height="10"></td>
            </tr>
          </tbody></table>
          <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%%!important" width="100%%">
            <tbody><tr>
              <td height="30"><br></td>
            </tr>
            <tr>
              <td width="10" valign="middle"><br></td>
              <td style="font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px">

                  <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Olá %s,</p>
                  <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c"> Obrigado por se registrar no sistema do If-Connect. Por favor clique no link abaixo para ativar sua conta: </p>
                  <blockquote style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px">
                    <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c"> <a href="%s">Ative agora</a> </p>
                  </blockquote>\s
                  O link irá expirar em 15 minutos. <p>Até logo!</p>

              </td>
              <td width="10" valign="middle"><br></td>
            </tr>
            <tr>
              <td height="30"><br></td>
            </tr>
          </tbody></table><div class="yj6qo"></div><div class="adL">

        </div></div>
        """.formatted(name, link);
    }

    public String codeEmail(String name, int value){
        return """
        <div style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c">
            <span style="display:none;font-size:1px;color:#fff;max-height:0"></span>
    
            <table role="presentation" width="100%%" style="border-collapse:collapse;min-width:100%%;width:100%%!important" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <td width="100%%" height="53" bgcolor="#0b0c0c">
    
                            <table role="presentation" width="100%%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                                <tbody>
                                    <tr>
                                        <td width="70" bgcolor="#0b0c0c" valign="middle">
                                            <table role="presentation" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                                <tbody>
                                                    <tr>
                                                        <td style="padding-left:10px">
    
                                                        </td>
                                                        <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px">
                                                            <span style="font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block">Alteração de senha</span>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
    
                        </td>
                    </tr>
                </tbody>
            </table>
    
            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%%!important" width="100%%">
                <tbody>
                    <tr>
                        <td width="10" height="10" valign="middle"></td>
                        <td>
    
                            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                                <tbody>
                                    <tr>
                                        <td bgcolor="#1D70B8" width="100%%" height="10"></td>
                                    </tr>
                                </tbody>
                            </table>
    
                        </td>
                        <td width="10" valign="middle" height="10"></td>
                    </tr>
                </tbody>
            </table>
    
            <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%%!important" width="100%%">
                <tbody>
                    <tr>
                        <td height="30"><br></td>
                    </tr>
                    <tr>
                        <td width="10" valign="middle"><br></td>
                        <td style="font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px">
    
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Olá %s,</p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Recebemos uma solicitação de alteração de senha, se não foi você, desconsidere esse email. </p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Seu código de verificação é: <strong>%d</strong></p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Por favor, insira este código para validar sua conta.</p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">O código irá expirar em 15 minutos.</p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Até logo!</p>
    
                        </td>
                        <td width="10" valign="middle"><br></td>
                    </tr>
                    <tr>
                        <td height="30"><br></td>
                    </tr>
                </tbody>
            </table>
            <div class="yj6qo"></div>
            <div class="adL"></div>
        </div>
        """.formatted(name, value);
    }

    public String confirmedPage(){
        return """
        <!DOCTYPE html>
        <html lang="en">
        
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Email Confirmado</title>
            <style>
                body {
                    font-family: Helvetica, Arial, sans-serif;
                    font-size: 16px;
                    margin: 0;
                    padding: 0;
                    color: #0b0c0c;
                    background-image: url('https://svgur.com/i/14Vc.svg');
                    background-size: cover;
                    background-position: center;
                    background-repeat: no-repeat;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }
        
                .container {
                    width: 100%;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #fff;
                    padding: 40px;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }
        
                .top-bar {
                    background-color: #0b0c0c;
                    color: #fff;
                    padding: 10px 0;
                    text-align: center;
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                }
        
                h1 {
                    font-size: 28px;
                    margin-bottom: 30px;
                }
        
                p {
                    font-size: 19px;
                    line-height: 1.6;
                    margin-bottom: 20px;
                }
        
                a {
                    color: #1D70B8;
                    text-decoration: none;
                }
        
                a:hover {
                    text-decoration: underline;
                }
            </style>
        </head>
        
        <body>
            <div class="top-bar">
                <h2>IF-Connect</h2>
            </div>
            <div class="container">
                <h1>Email Confirmado com Sucesso</h1>
                <p>Obrigado por confirmar seu email no sistema do if-connect. Sua conta agora está ativa.</p>
                <p>Faça login no aplicativo para usar o sistema</p>
            </div>
        </body>
        
        </html>
        """;
    }

    public String erroConfirmPage(String message){
        return """
        <!DOCTYPE html>
        <html lang="en">
        
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Falha ao confirmar email</title>
            <style>
                body {
                    font-family: Helvetica, Arial, sans-serif;
                    font-size: 16px;
                    margin: 0;
                    padding: 0;
                    color: #0b0c0c;
                    background-image: url('https://svgur.com/i/14Vc.svg');
                    background-size: cover;
                    background-position: center;
                    background-repeat: no-repeat;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }
        
                .container {
                    width: 100%%;
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #fff;
                    padding: 40px;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }
        
                .top-bar {
                    background-color: #0b0c0c;
                    color: #fff;
                    padding: 10px 0;
                    text-align: center;
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%%;
                }
        
                h1 {
                    font-size: 28px;
                    margin-bottom: 30px;
                }
        
                p {
                    font-size: 19px;
                    line-height: 1.6;
                    margin-bottom: 20px;
                }
        
                a {
                    color: #1D70B8;
                    text-decoration: none;
                }
        
                a:hover {
                    text-decoration: underline;
                }
            </style>
        </head>
        
        <body>
            <div class="top-bar">
                <h2>IF-Connect</h2>
            </div>
            <div class="container">
                <h1>Não foi possível confirmar o Email</h1>
                <p>%s</p>
                <p>Se estiver tendo problemas contacte o suporte!</p>
            </div>
        </body>
        
        </html>
        """.formatted(message);
    }

}