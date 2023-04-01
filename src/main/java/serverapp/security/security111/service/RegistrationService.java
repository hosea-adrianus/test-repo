package serverapp.security.security111.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import serverapp.security.security111.models.AppUser;
import serverapp.security.security111.models.AppUserRole;
import serverapp.security.security111.models.RegistrationRequest;

@Service
@AllArgsConstructor
public class RegistrationService {
    private AppUserService appUserService;
    private EmailValidation emailValidation;
    public String register(RegistrationRequest request){
        boolean isValid = emailValidation.test(request.getEmail());
        if(!isValid){
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
