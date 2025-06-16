package com.example.booking.domain;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginValidationUseCase {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /*public boolean validateLogin(@NotNull String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.toLowerCase().endsWith("@invalid.com");
    }*/

    public boolean validateLogin(@NotNull String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        email = email.trim();
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches() || email.toLowerCase().endsWith("@invalid.com")) {
            return false;
        }
        return true;
    }
}
