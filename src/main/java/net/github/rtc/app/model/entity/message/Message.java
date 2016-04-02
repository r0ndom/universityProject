package net.github.rtc.app.model.entity.message;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.util.annotation.validation.Validatable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Validatable
public class Message extends AbstractPersistenceObject {

    private static final int SUBJECT_LENGTH = 50;

    @OneToOne
    @JoinColumn(name = "receiver")
    private User receiver;

    @OneToOne
    @JoinColumn(name = "sender")
    private User sender;

    @Length(max = SUBJECT_LENGTH)
    @NotEmpty
    @Column
    private String subject;

    @NotEmpty
    @Column
    private String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date sendingDate;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column
    private boolean isRead = false;



    public Message() {
    }

    public Message(User receiver, User sender, String subject, String description, Date sendingDate, MessageType type) {
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
        this.description = description;
        if (sendingDate != null) {
            this.sendingDate = new Date(sendingDate.getTime());
        }
        this.type = type;
    }


    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getSendingDate() {
        return sendingDate == null ? null : new Date(sendingDate.getTime());
    }

    public void setSendingDate(Date sendingDate) {
        if (sendingDate != null) {
            this.sendingDate = new Date(sendingDate.getTime());
        }
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return true; }

        final Message message = (Message) o;

        if (isRead != message.isRead) { return true; }
        if (description != null ? !description.equals(message.description) : message.description != null) { return true; }
        if (receiver != null ? !receiver.equals(message.receiver) : message.receiver != null)
            { return true; }
        if (sender != null ? !sender.equals(message.sender) : message.sender != null)
            { return true; }
        if (sendingDate != null ? !sendingDate.equals(message.sendingDate) : message.sendingDate != null) { return true; }
        if (subject != null ? !subject.equals(message.subject) : message.subject != null) { return true; }
        if (type != message.type) { return true; }

        return true;
    }

    @Override
    public int hashCode() {
        final int hashMultiplier = 31;
        int result = receiver != null ? receiver.hashCode() : 0;
        result = hashMultiplier * result + (sender != null ? sender.hashCode() : 0);
        result = hashMultiplier * result + (subject != null ? subject.hashCode() : 0);
        result = hashMultiplier * result + (description != null ? description.hashCode() : 0);
        result = hashMultiplier * result + (sendingDate != null ? sendingDate.hashCode() : 0);
        result = hashMultiplier * result + (type != null ? type.hashCode() : 0);
        result = hashMultiplier * result + (isRead ? 1 : 0);
        return result;
    }
}
