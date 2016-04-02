package net.github.rtc.app.service.social;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.social.user.SocialUserProvider;
import net.github.rtc.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class AccountConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;
    @Autowired
    private SocialUserProvider socialUserProvider;

    @Override
    public String execute(Connection<?> connection) {
        final User user = socialUserProvider.getUser(connection);
        userService.createWithSocial(connection);
        return user.getEmail();
    }
}
