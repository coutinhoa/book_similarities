package com.example.booking;

import com.example.booking.domain.LoginValidationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginValidationUseCaseTest {

    @InjectMocks
    public LoginValidationUseCase loginValidationUseCase;

    @Test
    void validateLogin_shouldReturnTrue_whenEmailIsValid() {
        String validEmail = "email@email.com";

        boolean result = loginValidationUseCase.validateLogin(validEmail);

        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid-email-format", "email@invalid.com" })
    void validateLogin_shouldReturnFalse_forInvalidEmails(String invalidEmail) {
        boolean result = loginValidationUseCase.validateLogin(invalidEmail);
        assertFalse(result);
    }


    @ParameterizedTest(name = "{index} => input=''{0}'', expected=''{1}''")
    @MethodSource("arguments")
    void testingParameterizedTests(String input, boolean expected) {
        boolean result = loginValidationUseCase.validateLogin(input);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("invalid-email-format", false),
                Arguments.of("email@invalid.com", false),
                Arguments.of("email@email.com", true)
        );
    }
}