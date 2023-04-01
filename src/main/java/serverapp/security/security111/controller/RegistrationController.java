package serverapp.security.security111.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import serverapp.security.security111.models.RegistrationRequest;
import serverapp.security.security111.service.RegistrationService;

@RestController
@RequestMapping(path = "v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
