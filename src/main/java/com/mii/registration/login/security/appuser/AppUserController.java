package com.mii.registration.login.security.appuser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    @GetMapping("/")
    public String home(){
        return "Homepage";
    }
}
