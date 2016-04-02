package net.github.rtc.app.model.entity.activity;

public enum ActivityEntity {
    USER, COURSE, NEWS;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "User";
                break;
            case 1:
                name = "Course";
                break;
            case 2:
                name = "News";
                break;
            default:
                name = "Incorrect entity";
                break;
        }
        return name;
    }
}
