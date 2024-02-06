package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.dto.SignInRequest;
import ru.gb.springdemo.dto.SignUpRequest;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;
import ru.gb.springdemo.response.JwtAuthenticationResponse;
import ru.gb.springdemo.security.UserDetail;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        List< Role > roleList = Arrays.asList(new Role(UUID.randomUUID(),"USER"), new Role(UUID.randomUUID(),"GUEST"));
        Person user = new Person();
        user.setName(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(roleList);
                /*
                User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.roleList).
                .build();
                 */

        userService.savePerson(user);

        UserDetail userDetail = new UserDetail(user);

        var jwt = jwtService.generateToken(userDetail);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
