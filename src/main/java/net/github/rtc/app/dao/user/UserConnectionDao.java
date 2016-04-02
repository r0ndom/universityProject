package net.github.rtc.app.dao.user;


import net.github.rtc.app.model.entity.user.UserConnection;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

public interface UserConnectionDao {
    List<String> findUserIdsWithConnection(String providerId, String providerUserId);
    Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds);

    List<UserConnection> get(String userId);
    List<UserConnection> get(String userId, String providerId);
    List<UserConnection> getAll(String userId, MultiValueMap<String, String> providerUsers);
    List<UserConnection> getPrimary(String userId, String providerId);


    UserConnection get(String userId, String providerId, String providerUserId);

    int getRank(String userId, String providerId);

    void remove(String userId, String providerId);
    void remove(String userId, String providerId, String providerUserId);

    String createUserConnection(UserConnection userConnection);

    UserConnection updateUserConnection(UserConnection userConnection);

    UserConnection findById(String id);
    UserConnection findByUsername(String id);
}
