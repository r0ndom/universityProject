package net.github.rtc.app.utils.enums;

import java.util.*;

public final class EnumHelper {

    private EnumHelper() { }

    /**
     * Convert Enumeration type to List
     * @param enumClass enumeration type
     * @return enumClass items as List
     */
    public static  <E extends Enum<E>>  List<E> findAll(Class<E>  enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    /**
     * Get names of enumeration class
     * @param enumClass enumeration type
     * @return List with enumeration names
     */
    public static  <E extends Enum<E>>  List<String> getNames(Class<E> enumClass) {
        final List<String> res = new ArrayList<>();

        for (final  E value : enumClass.getEnumConstants()) {
            res.add(value.name());
        }
        return res;
    }

    /**
     * Get values of enumeration class
     * @param enumClass enumeration type
     * @return List with enumeration values
     */
    public static  <E extends Enum<E>>  List<String> getValues(Class<E> enumClass) {
        final List<String> res = new ArrayList<>();

        for (final  E value : enumClass.getEnumConstants()) {
            res.add(value.toString());
        }
        return res;
    }

    /**
     * Get map where key equal enum name and value equal enum values
     * @param enumClass enumeration type
     * @return Map formed from enum
     */
    public static  <E extends Enum<E>>  Map<String, String> createNameValueMap(Class<E> enumClass) {
        final Map<String, String> dictionary = new HashMap<>();
        for (E value : findAll(enumClass)) {
            dictionary.put(value.name(), value.toString());
        }
        return dictionary;
    }

    /**
     * Check if enumeration type has some name
     * @param enumClass enumeration type
     * @param items name which we want to check
     * @return true if name was found, otherwise false
     */
    public static <E extends Enum<E>> boolean containsName(Class<E>  enumClass, String items) {
        for (E c : enumClass.getEnumConstants()) {
            if (c.name().equals(items)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if enumeration type contains some value
     * @param enumClass enumeration type
     * @param items value which we want to check
     * @return true if value was found, otherwise false
     */
    public static <E extends Enum<E>> boolean containsValue(Class<E> enumClass, String items) {
        for (E c : enumClass.getEnumConstants()) {
            if (c.toString().equals(items)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get enum type from String name
     * @param enumClass enumeration type
     * @param roleName name to find
     * @throws java.lang.IllegalArgumentException if cannot found such name
     * @return
     */
    public static <E extends Enum<E>> E getTypeByString(Class<E>  enumClass, String roleName) {
        for (E type :  enumClass.getEnumConstants()) {
            if (type.name().equals(roleName)) {
                return type;
            }
        }
        throw new IllegalArgumentException(enumClass.getSimpleName() + " doesn't contains " + roleName);
    }
}
