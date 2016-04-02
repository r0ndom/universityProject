package net.github.rtc.app.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


@Profile("dev")
@Component("allowEncryptionWithoutJCE")
public class AllowEncryptionWithoutJCE implements InitializingBean {
    /**
     * After all bean properties set, this class set variable "isRestricted" in JceSecurity class to false
     * @throws java.lang.Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            final Field field = Class.forName("javax.crypto.JceSecurity").
                    getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
