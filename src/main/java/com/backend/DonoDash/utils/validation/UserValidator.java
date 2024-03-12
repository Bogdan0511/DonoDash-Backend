package com.backend.DonoDash.utils.validation;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private static final String NAME_REGEX = "^[a-zA-Z\\s]{1,50}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,50}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9 ]{1,20}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[.,_!?:;])[A-Za-z\\d.,_!?:;]{8,}$";

    public boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public boolean validateName(String name) {
        return !(name.matches(NAME_REGEX));
    }

    public boolean validateUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
