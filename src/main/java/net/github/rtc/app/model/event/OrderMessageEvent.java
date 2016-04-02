package net.github.rtc.app.model.event;

import net.github.rtc.app.model.entity.message.Message;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class OrderMessageEvent extends ApplicationEvent {

    private List<Message> messages;

    public OrderMessageEvent(Object source, List<Message> messages) {
        super(source);
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
