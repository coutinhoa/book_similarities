package com.example.booking;

import com.example.booking.domain.LoginValidationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
}