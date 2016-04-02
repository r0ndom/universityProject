package net.github.rtc.app.service.security;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    /**
     * Performs authentication
     * @param authentication - the authentication request object.
     * @return a fully authenticated object including credentials, cannot be null
     */
    @Override
    @Nonnull
    public Authentication authenticate(final Authentication authentication) {
        final String username = authentication.getName();
        final String password = (String) authentication.getCredentials();

        if (username == null || username.trim().isEmpty()) {
            throw new BadCredentialsException("Email cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new BadCredentialsException("Password cannot be empty");
        }

        final User user = userService.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BadCredentialsException("Contact the administrator "
                    + "tatyana.bulanaya@gmail.com");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        final Collection<? extends GrantedAuthority> authorities
                = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password,
                authorities);
    }

    /**
     * Check if AuthenticationProvider supports the indicated Authentication object.
     * @param aClass - the authentication object.
     * @return true if supporting
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return true;
    }
}
