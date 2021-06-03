package com.example.gitverificationbot;

import com.example.gitverificationbot.Common.MessageTemplateVerifier;
import org.junit.jupiter.api.Test;
import static com.example.gitverificationbot.Common.MessageTemplateVerifier.process;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTemplateVerifierTest extends AbstractTest {

    @Test
    public void processLeetcodeAddedTest() {
        String  test = process("LEETCODE 1022 Added 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);

    }

    @Test
    public void processLeetcodeFixedTest() {
        String test = process("LEETCODE 1022 Fixed 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processLeetcodeDeletedTest() {
        String test = process("LEETCODE 1022 Deleted 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processLeetcodeRefactoredTest() {
        String test = process("LEETCODE 1022 Refactored 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processGeneratorRefactoredTest() {
        String test = process("GENERATOR 1022 Refactored 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processGeneratorAddedTest() {
        String test = process("GENERATOR 1022 Added 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processGeneratorFixedTest() {
        String test = process("GENERATOR 1022 Fixed 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processGeneratorMovedTest() {
        String test = process("GENERATOR 1022 Moved 123");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.OK_RESULT);
    }

    @Test
    public void processErrorMessageTest() {
        String test = process("Привет, я сделял дз, проверьте, ато я валнуюсь");
        assertEquals(test, MessageTemplateVerifier.VERIFICATION_RESULT + MessageTemplateVerifier.ERROR_RESULT);
    }
}
