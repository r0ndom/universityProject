package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that creates register notification message
 */
@Component
public class MailMessageBuilder {

    private static final String LINK = "http://146.185.176.193/rtc-app/user/courses";
    private static final String SENDER_MAIL = "rtcsender@gmail.com";
    private static final String REGISTRATION_SUBJECT = "Registration confirmation";

    private static final String TEMPLATE_PATH = "templates/registrationMail.ftl";

    @Autowired
    private TemplateStringBuilder templateStringBuilder;

    /**
     * Build mail register notification based on User object
     * @param user user that needs register notification
     * @return built SimpleMailMessage, cannot be null
     */
    @Nonnull
    public SimpleMailMessage build(User user) {
        return build(user.getName(), user.getEmail());
    }

    /**
     * Create mail register notification based on user name and email
     * @param name name of the receiver user
     * @param mail email address of the receiver
     * @return built SimpleMailMessage, cannot be null
     */
    @Nonnull
    public SimpleMailMessage build(String name, String mail) {

        final Map<String, Object> templateParam = new HashMap<>();
        templateParam.put("userName", name);
        templateParam.put("link", LINK);

        String text = templateStringBuilder.build(TEMPLATE_PATH, templateParam);
        if (text.equals(TemplateStringBuilder.UNKNOWN_VALUE)) {
            text = "Welcome," + name + "! ";
        }

        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_MAIL);
        message.setTo(mail);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(text);

        return message;
    }

}
