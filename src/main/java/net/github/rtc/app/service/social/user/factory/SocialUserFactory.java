package net.github.rtc.app.service.social.user.factory;

import net.github.rtc.app.model.entity.user.User;
import org.springframework.social.connect.Connection;

public interface SocialUserFactory<A> {

    User getUser(Connection<A> connection);
}
