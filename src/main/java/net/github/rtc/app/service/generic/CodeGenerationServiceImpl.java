package net.github.rtc.app.service.generic;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.UUID;

@Component
public class CodeGenerationServiceImpl implements CodeGenerationService {
    @Nonnull
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
