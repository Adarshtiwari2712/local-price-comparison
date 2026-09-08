package com.example.local.service;
import com.example.local.dto.AuthResponseDTO;
import com.example.local.dto.LoginRequest;
import com.example.local.dto.RegisterRequest;
import com.example.local.exception.EmailAlreadyExistsException;
import com.example.local.exception.InvalidCredentialsException;
import com.example.local.model.User;
import com.example.local.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already registerd");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                "USER"
        );
        User savedUser = userRepository.save(user);

        return new AuthResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                null
        );
    }

    public AuthResponseDTO login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(
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
