import africa.semicolon.toDo.services.passwordHashing.PasswordHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashingExample {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordHash hash = new PasswordHash();
        String rawPassword = "mySecurePassword";
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        boolean matches = passwordEncoder.matches(rawPassword, hashedPassword);
        System.out.println("Password matches: " + matches);

//        another one

        String rawPassword2 = "abimbolajolayemi";
        String hashedPassword2 = passwordEncoder.encode(rawPassword2);
        System.out.println("Hashed Password: " + hashedPassword2);

        boolean paswordMatches = passwordEncoder.matches(rawPassword2, hashedPassword2); // prints true
        System.out.println("Password matches: " + paswordMatches);

        boolean paswordMatch = passwordEncoder.matches(rawPassword, hashedPassword2); //prints false
        System.out.println("Password matches: " + paswordMatch);

        String decodedPassword = passwordEncoder.toString();
        System.out.println("Decoded Password: " + decodedPassword);

        String newHashedPw = hash.hashPassword(rawPassword2);
        System.out.println("New Hashed Password: " + newHashedPw);
    }
}
