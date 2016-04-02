package net.github.rtc.app.service.mail;


import org.springframework.mail.SimpleMailMessage;

/**
 * The service class that is responsible for sending messages on users' email
 */
public interface MailService {

    /**
     * Send mail from one user to another
     * @param from sender address
     * @param to receiver address
     * @param subject topic of the mail
     * @param msg text of the message
     */
    void sendMail(String from, String to, String subject, String msg);

    /**
     * Send mail message
     * @param msg mail message
     */
    void sendMail(SimpleMailMessage msg);
}
