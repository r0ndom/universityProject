package net.github.rtc.app.service.builder.message;

import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.message.MessageType;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.service.builder.TemplateStringBuilder;
import net.github.rtc.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Object that helps to build list of messages
 */
@Component("orderResponseMessageBuilder")
public class OrderResponseMessageBuilder {

    private static final String SUBJECT_TEMPLATE_PATH = "/templates/messages/order/response/subject.ftl";
    private static final String DESCRIPTION_TEMPLATE_PATH = "/templates/messages/order/response/description.ftl";
    private static final String MESSAGE_TEXT_ACCEPTED = "message.text.accepted";
    private static final String MESSAGE_TEXT_REJECTED = "message.text.rejected";
    private static final String STATUS = "status";
    private static final String IS_REJECTED = "isRejected";
    private static final String REASON = "reason";

    @Autowired
    private DateService dateService;

    @Autowired
    private TemplateStringBuilder templateStringBuilder;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Build list of messages from course order
     * @param order user course order
     * @return list of messages
     */
    @Nonnull
    public List<Message> build(UserCourseOrder order) {
        final Message msg = new Message();
        final User receiver = userService.findByCode(order.getUserCode());
        msg.setSender(AuthorizedUserProvider.getAuthorizedUser());
        msg.setReceiver(receiver);
        msg.setSendingDate(dateService.getCurrentDate());
        msg.setType(MessageType.SYSTEM);
        msg.setSubject(getSubjectText(order.getStatus()));
        msg.setDescription(getDescriptionText(order));
        return Arrays.asList(msg);
    }

    /**
     * Get subject text depends on user request status
     * @param status the status of user request
     * @return subject text
     */
    @Nonnull
    private String getSubjectText(final UserRequestStatus status) {
        switch (status) {
            case ACCEPTED:
                return templateStringBuilder.build(SUBJECT_TEMPLATE_PATH,
                        new HashMap<String, Object>() { {
                            put(STATUS, getLocalizedParam(MESSAGE_TEXT_ACCEPTED));
                        } });
            case REJECTED:
                return templateStringBuilder.build(SUBJECT_TEMPLATE_PATH,
                        new HashMap<String, Object>() { {
                            put(STATUS, getLocalizedParam(MESSAGE_TEXT_REJECTED));
                        } });
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Build description text of the message from user course order
     * @param order user course order
     * @return description of the message
     */
    @Nonnull
    private String getDescriptionText(final UserCourseOrder order) {
        final Map<String, Object> templateObj = new HashMap<>();
        templateObj.put("courseName", courseService.findByCode(order.getCourseCode()).getName());
        templateObj.put("expertLastName", AuthorizedUserProvider.getAuthorizedUser().getSurname());
        templateObj.put("expertFirstName", AuthorizedUserProvider.getAuthorizedUser().getName());
        templateObj.put("responseDate", order.getResponseDate());
        if (order.getStatus() == UserRequestStatus.ACCEPTED) {
            templateObj.put(STATUS, getLocalizedParam(MESSAGE_TEXT_ACCEPTED));
            templateObj.put(IS_REJECTED, false);
            templateObj.put(REASON, "");
        } else {
            templateObj.put(STATUS, getLocalizedParam(MESSAGE_TEXT_REJECTED));
            templateObj.put(IS_REJECTED, true);
            templateObj.put(REASON, order.getRejectReason());
        }
        return templateStringBuilder.build(DESCRIPTION_TEMPLATE_PATH, templateObj);
    }

    /**
     * Get localized parameter from message code
     * @param code message code
     * @return localized parameter
     */
    @Nonnull
    private String getLocalizedParam(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
