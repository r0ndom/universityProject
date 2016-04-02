package net.github.rtc.app.service.social;

import net.github.rtc.app.dao.user.UserConnectionDao;
import net.github.rtc.app.model.entity.user.UserConnection;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class HibernateConnectionRepository implements ConnectionRepository {

    private UserConnectionDao userConnectionDao;
    private final String userId;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final TextEncryptor textEncryptor;
    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

    public HibernateConnectionRepository(String userId, UserConnectionDao userConnectionDao,
                                         ConnectionFactoryLocator connectionFactoryLocator,
                                         TextEncryptor textEncryptor) {
        this.userConnectionDao = userConnectionDao;
        this.userId = userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    @Override
    @Transactional
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        final List<Connection<?>> resultList = connectionMapper.mapEntities(userConnectionDao.get(userId));

        final MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<>();
        final Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
        }
        for (Connection<?> connection : resultList) {
            final String providerId = connection.getKey().getProviderId();
            if (connections.get(providerId).size() == 0) {
                connections.put(providerId, new LinkedList<Connection<?>>());
            }
            connections.add(providerId, connection);
        }
        return connections;
    }

    @Override
    @Transactional
    public List<Connection<?>> findConnections(String providerId) {
        return connectionMapper.mapEntities(userConnectionDao.get(userId, providerId));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        final List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    @Transactional
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
        if (providerUsers == null || providerUsers.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        final List<Connection<?>> resultList = connectionMapper.mapEntities(userConnectionDao.getAll(userId, providerUsers));

        final MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<>();
        for (Connection<?> connection : resultList) {
            final String providerId = connection.getKey().getProviderId();
            final List<String> userIds = providerUsers.get(providerId);
            List<Connection<?>> connections = connectionsForUsers.get(providerId);
            if (connections == null) {
                connections = new ArrayList<>(userIds.size());
                for (int i = 0; i < userIds.size(); i++) {
                    connections.add(null);
                }
                connectionsForUsers.put(providerId, connections);
            }
            final String providerUserId = connection.getKey().getProviderUserId();
            final int connectionIndex = userIds.indexOf(providerUserId);
            connections.set(connectionIndex, connection);
        }
        return connectionsForUsers;
    }

    @Override
    @Transactional
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        try {
            return connectionMapper.mapEntity(userConnectionDao.get(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId()));
        } catch (Exception e) {
            throw new NoSuchConnectionException(connectionKey);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        final String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        final String providerId = getProviderId(apiType);
        final Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        final String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    @Transactional
    public void addConnection(Connection<?> connection) {
        try {
            final ConnectionData data = connection.createData();
            final int rank = userConnectionDao.getRank(userId, data.getProviderId());

            final UserConnection userConnection = new UserConnection();
            userConnection.setUserId(userId);
            userConnection.setProviderId(data.getProviderId());
            userConnection.setProviderUserId(data.getProviderUserId());
            userConnection.setRank(rank);
            userConnection.setDisplayName(data.getDisplayName());
            userConnection.setProfileUrl(data.getProfileUrl());
            userConnection.setImageUrl(data.getImageUrl());
            userConnection.setAccessToken(data.getAccessToken());
            userConnection.setSecret(data.getSecret());
            userConnection.setRefreshToken(data.getRefreshToken());
            userConnection.setExpireTime(data.getExpireTime());

            userConnectionDao.createUserConnection(userConnection);
        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }

    @Override
    @Transactional
    public void updateConnection(Connection<?> connection) {
        final ConnectionData data = connection.createData();

        final UserConnection su = userConnectionDao.get(userId, data.getProviderId(), data.getProviderUserId());
        if (su != null) {
            su.setDisplayName(data.getDisplayName());
            su.setProfileUrl(data.getProfileUrl());
            su.setImageUrl(data.getImageUrl());
            su.setAccessToken(encrypt(data.getAccessToken()));
            su.setSecret(encrypt(data.getSecret()));
            su.setRefreshToken(encrypt(data.getRefreshToken()));
            su.setExpireTime(data.getExpireTime());

            userConnectionDao.updateUserConnection(su);
        }
    }

    @Override
    @Transactional
    public void removeConnections(String providerId) {
        userConnectionDao.remove(userId, providerId);
    }

    @Override
    @Transactional
    public void removeConnection(ConnectionKey connectionKey) {
        userConnectionDao.remove(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
    }

    @Transactional
    private Connection<?> findPrimaryConnection(String providerId) {
        final List<Connection<?>> connections = connectionMapper.mapEntities(userConnectionDao.getPrimary(userId, providerId));
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }

    private final class ServiceProviderConnectionMapper {

        public List<Connection<?>> mapEntities(List<UserConnection> socialUsers) {
            final List<Connection<?>> result = new ArrayList<>();
            for (UserConnection su : socialUsers) {
                result.add(mapEntity(su));
            }
            return result;
        }

        public Connection<?> mapEntity(UserConnection socialUser) {
            final ConnectionData connectionData = mapConnectionData(socialUser);
            final ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(UserConnection socialUser) {
            return new ConnectionData(
                    socialUser.getProviderId(),
                    socialUser.getProviderUserId(),
                    socialUser.getDisplayName(),
                    socialUser.getProfileUrl(),
                    socialUser.getImageUrl(),
                    decrypt(socialUser.getAccessToken()),
                    decrypt(socialUser.getSecret()),
                    decrypt(socialUser.getRefreshToken()),
                    expireTime(socialUser.getExpireTime()
                    ));
        }

        private String decrypt(String encryptedText) {
            return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
        }

        private Long expireTime(Long expireTime) {
            return expireTime == null || expireTime == 0 ? null : expireTime;
        }

    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }
}
