package net.github.rtc.app.service.social;

import net.github.rtc.app.dao.user.UserConnectionDao;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HibernateUserConnectionRepository implements UsersConnectionRepository {


    private UserConnectionDao userConnectionDao;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    public HibernateUserConnectionRepository(UserConnectionDao userConnectionDao, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.userConnectionDao = userConnectionDao;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    /**
     * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
     * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
     * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
     * @see #findUserIdsWithConnection(Connection)
     */
    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    @Transactional
    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        final ConnectionKey key = connection.getKey();
        final List<String> localUserIds = userConnectionDao.findUserIdsWithConnection(key.getProviderId(), key.getProviderUserId());
        if (localUserIds.size() == 0 && connectionSignUp != null) {
            final String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null) {
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }
        return localUserIds;
    }

    @Transactional
    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return userConnectionDao.findUserIdsConnectedTo(providerId, providerUserIds);
    }

    @Override
    @Transactional
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new HibernateConnectionRepository(userId, userConnectionDao, connectionFactoryLocator, textEncryptor);
    }
}
