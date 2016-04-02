package net.github.rtc.app.service.message;

import net.github.rtc.app.model.dto.message.MessageDto;
import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.message.MessageStatus;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.MessageSearchFilter;

/**
 * Service that is responsible for sending messages from system or other users
 * on user's account and operation on Message model class
 * @see net.github.rtc.app.model.entity.message.Message
 */
public interface MessageService extends GenericService<Message> {

    /**
     * Search messages for user that is presented as DTOs
     * @param searchFilter object that contains search params
     * @see net.github.rtc.app.model.dto.filter.MessageSearchFilter
     *
     * @return search results for user view presented as DTO
     * @see net.github.rtc.app.model.dto.message.MessageDto
     */
    SearchResults<MessageDto> searchUserMessages(MessageSearchFilter searchFilter);

    /**
     * If message has isRead property false set it to true
     * @see net.github.rtc.app.model.entity.message.Message
     * @param messageCode code of the message for what operation will be performed
     * @return updated message
     */
    Message getMessage(String messageCode);

    /**
     * Get how much unread messages user have
     * @param user user for what operation will be performed
     * @return count of unread messages
     */
    int getMessageCountByUserAndStatus(User user, MessageStatus status);

    /**
     * Create order response message abd persist its to db
     * @param order response order
     */
    void createOrderResponseMessage(UserCourseOrder order);

    /**
     * Create order send message abd persist its to db
     * @param order sent order
     */
    void createOrderSendMessage(UserCourseOrder order);
}
