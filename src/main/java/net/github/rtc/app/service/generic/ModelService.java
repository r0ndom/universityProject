package net.github.rtc.app.service.generic;

/**
 * Service that provides it's entity class for export
 * @param <T> class that provides it's data for export
 */
public interface ModelService<T> extends GenericService<T> {

    /**
     * Get generic type of class usually it's entity class
     * @return class generic class of ModelService interface
     */
    Class<T> getType();
}
