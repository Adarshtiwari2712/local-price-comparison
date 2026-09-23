package com.example.local.service;
import com.example.local.dto.AuthResponseDTO;
import com.example.local.dto.LoginRequest;
import com.example.local.exception.InvalidCredentialsException;
import com.example.local.model.LocalStore;
import com.example.local.model.User;
import com.example.local.repository.LocalStoreRepository;
import com.example.local.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.local.dto.ShopkeeperRegistrationRequest;
import com.example.local.exception.EmailAlreadyExistsException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LocalStoreRepository localStoreRepository;


    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            LocalStoreRepository localStoreRepository
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.localStoreRepository = localStoreRepository;
    }

    public String register(ShopkeeperRegistrationRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                "SHOPKEEPER"
        );
        User savedUser = userRepository.save(user);
        LocalStore store = new LocalStore(
                request.getStoreName(),
                request.getAddress(),
                request.getPhone()
        );

        store.setOwner(savedUser);
        localStoreRepository.save(store);
     return "Shopkeeper registered successfully";
    }

    public AuthResponseDTO login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if(!"SHOPKEEPER".equals(user.getRole()) ||
        !passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
