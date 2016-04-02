package net.github.rtc.app.service.generic;

/**
 * The service class that is responsible for generation of the string code identifier
 * for persistence objects
 */
public interface CodeGenerationService {

    /**
     * Generate code for persistence object
     * @return code identifier
     */
    String generate();
}
