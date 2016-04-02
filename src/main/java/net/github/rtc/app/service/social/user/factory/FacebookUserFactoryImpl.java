package net.github.rtc.app.service.social.user.factory;

import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.SocialMediaService;
import net.github.rtc.app.model.entity.user.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.support.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

public class FacebookUserFactoryImpl implements SocialUserFactory<Facebook> {

    @Override
    public User getUser(Connection<Facebook> connection) {
        final User user = new User();
        connection.fetchUserProfile().getEmail();
        final org.springframework.social.facebook.api.User fbProfile =
                connection.getApi().userOperations().getUserProfile();
        user.setName(fbProfile.getFirstName());
        user.setSurname(fbProfile.getLastName());
        user.setEmail(fbProfile.getEmail());

        user.setGender(fbProfile.getGender());
        user.setPhoto(fetchLargeProfilePictureUrl(connection, fbProfile.getId()));
        user.setBirthDate(new Date());
        user.setPassword("todoPassword");
        user.setPhone("todo");
        user.setEnglish(EnglishLevel.BASIC);
        user.setNote("todoNote");
        user.setSocialSignInProvider(SocialMediaService.FACEBOOK);

        user.setMiddleName("");
        user.setUniversity("");
        user.setCity("");
        user.setFaculty("");
        user.setSpeciality("");
        return user;
    }

    public String fetchLargeProfilePictureUrl(Connection<Facebook> connection, String userId) {
        final URI uri = URIBuilder.fromUri("https://graph.facebook.com/v2.3/" + userId + "/picture"
                + "?width=200&height=200&redirect=false").build();
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String response = connection.getApi().restOperations().getForObject(uri,  String.class);
            final JsonNode actualObj = mapper.readTree(response);
            return actualObj.get("data").get("url").getTextValue();
        } catch (IOException e) {
            return connection.getImageUrl();
        }
    }
}
