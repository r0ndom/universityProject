package net.github.rtc.app.service.social.user.factory;

import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.SocialMediaService;
import net.github.rtc.app.model.entity.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.social.vkontakte.api.VKontakteDate;
import org.springframework.social.vkontakte.api.VKontakteProfile;

import java.util.Calendar;

public class VkUserFactoryImpl implements SocialUserFactory<VKontakte> {

    @Override
    public User getUser(Connection<VKontakte> connection) {
        final User user = new User();
        connection.fetchUserProfile().getEmail();
        final VKontakteProfile vKontakteProfile = connection.getApi().usersOperations().getUser();
        user.setName(vKontakteProfile.getFirstName());
        user.setEmail(connection.fetchUserProfile().getUsername() + "@email.com");
        user.setSurname(vKontakteProfile.getLastName());
        user.setGender(vKontakteProfile.getGender());
        user.setPhoto(vKontakteProfile.getPhoto200());
        final Calendar c = Calendar.getInstance();
        final VKontakteDate date = vKontakteProfile.getBirthDate();
        c.set(date.getYear(), date.getMonth() - 1, date.getDay(), 0, 0);
        user.setBirthDate(c.getTime());
        user.setPassword("todoPassword");
        user.setPhone("todo");
        user.setEnglish(EnglishLevel.BASIC);
        user.setNote("todoNote");
        user.setSocialSignInProvider(SocialMediaService.VKONTAKTE);

        user.setMiddleName("");
        user.setUniversity("");
        user.setCity("");
        user.setFaculty("");
        user.setSpeciality("");
        return user;
    }
}
