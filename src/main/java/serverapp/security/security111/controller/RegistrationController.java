package serverapp.security.security111.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import serverapp.security.security111.models.RegistrationRequest;
import serverapp.security.security111.service.RegistrationService;

@RestController
@RequestMapping(path = "v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;

    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
