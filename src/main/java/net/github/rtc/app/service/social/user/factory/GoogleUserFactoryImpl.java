package net.github.rtc.app.service.social.user.factory;

import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.SocialMediaService;
import net.github.rtc.app.model.entity.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;

import java.util.Date;

public class GoogleUserFactoryImpl implements SocialUserFactory<Google> {

    @Override
    public User getUser(Connection<Google> connection) {
        final User user = new User();
        final Person person = connection.getApi().plusOperations().getGoogleProfile();

        user.setName(person.getGivenName());
        user.setSurname(person.getFamilyName());
        user.setEmail(person.getAccountEmail());

        user.setGender(person.getGender());

        String imageUrl = person.getImageUrl();
        //todo: better solution for image quality
        imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("sz")) + "sz=200";
        user.setPhoto(imageUrl);
        if (person.getBirthday() != null) {
            user.setBirthDate(person.getBirthday());
        } else {
            user.setBirthDate(new Date());
        }

        user.setPassword("todoPassword");
        user.setPhone("todo");
        user.setEnglish(EnglishLevel.BASIC);
        user.setNote("todoNote");
        user.setSocialSignInProvider(SocialMediaService.GOOGLE);

        user.setMiddleName("");
        user.setUniversity("");
        user.setCity("");
        user.setFaculty("");
        user.setSpeciality("");
        return user;
    }
}
