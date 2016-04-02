package net.github.rtc.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SimpleSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String s) {
        return userService.loadUserByUsername(s);
    }
}
