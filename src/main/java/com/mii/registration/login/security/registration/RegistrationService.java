package com.mii.registration.login.security.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    public String register(RegistrationRequest request) {
        return "works";
    }


}