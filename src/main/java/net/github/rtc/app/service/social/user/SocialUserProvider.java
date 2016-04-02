package net.github.rtc.app.service.social.user;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.social.user.factory.FacebookUserFactoryImpl;
import net.github.rtc.app.service.social.user.factory.GoogleUserFactoryImpl;
import net.github.rtc.app.service.social.user.factory.VkUserFactoryImpl;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.stereotype.Component;

@Component
public class SocialUserProvider {


    @SuppressWarnings("unchecked")
    public User getUser(Connection<?> connection) {
        User newUser = null;
        if (connection.getApi() instanceof VKontakte) {
            final VkUserFactoryImpl vkUserFactory = new VkUserFactoryImpl();
            newUser = vkUserFactory.getUser((Connection<VKontakte>) connection);
        }
        if (connection.getApi() instanceof Facebook) {
            final FacebookUserFactoryImpl facebookUserFactory = new FacebookUserFactoryImpl();
            newUser = facebookUserFactory.getUser((Connection<Facebook>) connection);
        }
        if (connection.getApi() instanceof Google) {
            final GoogleUserFactoryImpl googleUserFactory = new GoogleUserFactoryImpl();
            newUser = googleUserFactory.getUser((Connection<Google>) connection);
        }
        return newUser;
    }
}
