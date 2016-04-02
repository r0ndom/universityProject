package net.github.rtc.app.dao.message;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.user.User;

import javax.annotation.Resource;

/**
 * Data access object for Message domain class
 * @see net.github.rtc.app.model.entity.message.Message
 */
@Resource
public interface MessageDao  extends GenericDao<Message> {

    /**
     * Get count of  user messages with isRead = status
     * @param user user for what messages needs to be found
     * @return count of messages match status
     */
    int getMessageCountByUserAndRead(User user, boolean isRead);
}
