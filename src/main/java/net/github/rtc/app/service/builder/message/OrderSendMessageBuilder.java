package net.github.rtc.app.service.builder.message;

import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.message.MessageType;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.builder.TemplateStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Class that helps to build messages from user course order
 */
@Component("orderSendMessageBuilder")
public class OrderSendMessageBuilder {

    private static final String SUBJECT_TEMPLATE_PATH = "/templates/messages/order/request/subject.ftl";
    private static final String DESCRIPTION_TEMPLATE_PATH = "/templates/messages/order/request/description.ftl";
    @Autowired
    private DateService dateService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateStringBuilder templateStringBuilder;

    /**
     * Build list of messages from user course order
     * @param order user course order, cannot be null
     * @return list of messages
     */
    @Nonnull
    public List<Message> build(@Nonnull UserCourseOrder order) {
        final List<Message> msgList = new ArrayList<>();
        final Course course = courseService.findByCode(order.getCourseCode());

        for (User expert : course.getExperts()) {
            final Message msg = new Message();
            final User sender = userService.findByCode(order.getUserCode());
            msg.setSender(sender);
            msg.setSendingDate(dateService.getCurrentDate());
            msg.setType(MessageType.SYSTEM);
            msg.setSubject(getMessageSubject());
            msg.setDescription(getMessageDescription(order));
            msg.setReceiver(expert);
            msgList.add(msg);
        }
        return msgList;
    }

    /**
     * Get message subject
     * @return message subject represented by string value
     */
    @Nonnull
    private String getMessageSubject() {
        return templateStringBuilder.build(SUBJECT_TEMPLATE_PATH);
    }

    /**
     * Get message description from user course order
     * @param order user course order
     * @return message description represented by string value
     */
    @Nonnull
    private String getMessageDescription(@Nonnull UserCourseOrder order) {
        final User user =  userService.findByCode(order.getUserCode());

        final Map<String, Object> templateObj = new HashMap<>();
        templateObj.put("userName", user.getName());
        templateObj.put("userSurName",  user.getSurname());
        templateObj.put("courseName", courseService.findByCode(order.getCourseCode()).getName());
        templateObj.put("orderDate", order.getRequestDate());

        return templateStringBuilder.build(DESCRIPTION_TEMPLATE_PATH, templateObj);
    }


}
