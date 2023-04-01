package serverapp.security.security111.service;

import org.springframework.stereotype.Service;
import serverapp.security.security111.models.RegistrationRequest;

@Service
public class RegistrationService {
    public String register(RegistrationRequest request){
        return "Works";
    }
}
