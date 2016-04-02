package net.github.rtc.app.utils;
import net.github.rtc.util.annotation.ForExport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ExportFieldExtractor {

    private ExportFieldExtractor() {
    }

    public static List<Field> getFieldsFromClass(final Class exportClass, final List<String> fields) {
        final List<Field> classFields = new ArrayList<>();
        for (final Field f : exportClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(ForExport.class) && fields.contains(f.getAnnotation(ForExport.class).value())) {
                classFields.add(f);
                if (f.getAnnotation(ForExport.class).inculdeField().length != 0) {
                    classFields.addAll(getFieldsFromClass(f.getType(), Arrays.asList(f.getAnnotation(ForExport.class).inculdeField())));
                }
            }
        }
        return classFields;
    }

    public static List<String> getAvailableFieldList(final Class aClass) {
        final List<String> classFields = new ArrayList<>();
        for (final Field f : aClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(ForExport.class)) {
                classFields.add(f.getAnnotation(ForExport.class).value());
                if (f.getAnnotation(ForExport.class).inculdeField().length != 0) {
                    classFields.addAll(Arrays.asList(f.getAnnotation(ForExport.class).inculdeField()));
                }
            }
        }
        return classFields;
    }
}
