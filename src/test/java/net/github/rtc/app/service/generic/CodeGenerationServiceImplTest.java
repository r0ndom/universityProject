package net.github.rtc.app.service.generic;

import org.junit.Test;

import static org.junit.Assert.*;

public class CodeGenerationServiceImplTest {

    CodeGenerationService codeGenerationService = new CodeGenerationServiceImpl();

    @Test
    public void testGenerate() throws Exception {
        String generate = codeGenerationService.generate();
        assertTrue(generate.length()>10); // need to use current size code? (36)
        String generate2 = codeGenerationService.generate();
        assertTrue(generate.length()==generate2.length());
        String generate3 = codeGenerationService.generate();
        assertTrue(generate.length()==generate3.length());
    }
}