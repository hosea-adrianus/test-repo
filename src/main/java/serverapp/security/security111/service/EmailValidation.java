package serverapp.security.security111.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidation implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //Regex to validate email
        return true;
    }
}
