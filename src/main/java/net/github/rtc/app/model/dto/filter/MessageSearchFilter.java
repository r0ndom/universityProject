package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.message.MessageStatus;
import net.github.rtc.app.model.entity.user.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class MessageSearchFilter extends AbstractSearchFilter {

    private static final String SENDING_DATE = "sendingDate";
    private static final String IS_READ = "isRead";
    private static final String RECEIVER = "receiver";


    private MessageStatus status;
    private Date sendingDate;
    private char dateMoreLessEq;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }


    public Date getSendingDate() {
        return sendingDate == null ? null : new Date(sendingDate.getTime());
    }

    public void setSendingDate(Date sendingDate) {
        if (sendingDate != null) {
            this.sendingDate = new Date(sendingDate.getTime());
        }
    }

    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    @Override
    public Order order() {
        return Order.desc(SENDING_DATE);
    }

    @Override
    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);

        if (user != null && !("").equals(user.toString())) {
            criteria.add(Restrictions.eq(RECEIVER, user));
        }

        if (sendingDate != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(SENDING_DATE, sendingDate);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));
        }
        if (status != null) {
            switch (status) {
                case READ:  criteria.add(Restrictions.eq(IS_READ, true));
                    break;
                case UNREAD: criteria.add(Restrictions.eq(IS_READ, false));
                    break;
                default: break;
            }

        }

        return criteria;
    }
}
