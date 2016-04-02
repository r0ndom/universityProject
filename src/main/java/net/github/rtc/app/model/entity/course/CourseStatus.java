package net.github.rtc.app.model.entity.course;

import java.util.ArrayList;
import java.util.List;

public enum CourseStatus {
    DRAFT, PUBLISHED, ARCHIVED;

    public static List<String> getActiveStatus() {
        final List<String> stats = new ArrayList<>();
        stats.add(CourseStatus.DRAFT.name());
        stats.add(CourseStatus.PUBLISHED.name());
        return stats;
    }
}
