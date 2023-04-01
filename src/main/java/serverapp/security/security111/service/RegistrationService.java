package serverapp.security.security111.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serverapp.security.security111.models.AppUser;
import serverapp.security.security111.models.AppUserRole;
import serverapp.security.security111.models.ConfirmationToken;
import serverapp.security.security111.models.RegistrationRequest;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private AppUserService appUserService;
    private EmailValidation emailValidation;
    private ConfirmationTokenService confirmationTokenService;
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
    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()-> new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "Confirmed";
    }
}
