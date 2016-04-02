package net.github.rtc.app.model.entity.export;

import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.user.User;

public enum ExportClasses {
    Course(Course.class), User(User.class);
    private Class value;

    private ExportClasses(Class value) {
        this.value = value;
    }

    public Class getValue() {
        return value;
    }


}
