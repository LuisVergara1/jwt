package com.jwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.models.ERole;
import com.jwt.models.Role;
import com.jwt.models.User;
import com.jwt.payload.request.LoginRequest;
import com.jwt.payload.request.SignupRequest;
import com.jwt.payload.response.JwtResponse;
import com.jwt.payload.response.MessageResponse;
import com.jwt.repository.RoleRepository;
import com.jwt.repository.UserRepository;
import com.jwt.security.jwt.JwtUtils;
import com.jwt.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUserEntity(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
        .map(item->item.getAuthority())
        .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, 
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
         roles));

        }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {

        if(userRepository.existsByUsername(signupRequest.getUsername()))
        {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail()))
        {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Email is already in use"));
        }

        User user = new User(signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles==null)
        {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role ->{
                switch (role) {
                    case "admin":
                     Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                     .orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                     roles.add(adminRole);
                        break;

                    case "mod":
                    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(()-> new RuntimeException("Error: Role is not found"));
                    roles.add(modRole);
                    break;

                    default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Error: is not found"));
                    roles.add(userRole);
                        break;
                }
            });
        }
        
        user.setRoles(roles);
        userRepository.save(user);


        //Enviar Mail de Registro

        //emailService.sendEmail(user.getEmail(), "Registro exitoso", "¡Gracias por registrarte!");

        return ResponseEntity.ok(new MessageResponse("User registered successfull"));
        

    }

}
