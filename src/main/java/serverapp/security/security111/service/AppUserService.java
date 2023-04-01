package serverapp.security.security111.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import serverapp.security.security111.models.AppUser;
import serverapp.security.security111.models.ConfirmationToken;
import serverapp.security.security111.repository.AppUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
    public String signUpUser(AppUser appUser){
        String email = appUser.getEmail();
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        boolean userExist = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        if(userExist){
            throw new IllegalStateException("email already taken");
        }
       String encodePass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePass);
        appUserRepository.save(appUser);
        // Send Confirmation Token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9]+(?:\\\\.[a-zA-Z0-9]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
