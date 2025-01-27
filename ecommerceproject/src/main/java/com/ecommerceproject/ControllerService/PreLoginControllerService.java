package com.ecommerceproject.ControllerService;

import com.ecommerceproject.Dto.AuthRequest;
import com.ecommerceproject.Dto.AuthResponse;
import com.ecommerceproject.Entity.Role;
import com.ecommerceproject.Entity.User;
import com.ecommerceproject.EntityService.UserService;
import com.ecommerceproject.Exception.EmailAlreadyRegisteredException;
import com.ecommerceproject.Exception.IncorrectEmailException;
import com.ecommerceproject.Exception.UsernameisTakenException;
import com.ecommerceproject.JwtService.CustomUserDetailsService;
import com.ecommerceproject.JwtService.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PreLoginControllerService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PreLoginControllerService(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                     CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(User ut)   {
        if (userService.loginByEmail(ut.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException("Email is Already Registered ,Try to login");
        }
        if (userService.findById(ut.getUsername()) != null) {
            throw new UsernameisTakenException("Username is taken ,Try another username");
        }

        encodePasswordAndAssignRoles(ut);
        userService.save(ut);
        return ResponseEntity.ok("Registered Successfully");
    }

    public ResponseEntity<?> loginById(AuthRequest authRequest) {
        try {
            authenticate(authRequest.getId(), authRequest.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return generateLoginResponse(authRequest.getId());
    }

    public ResponseEntity<?> loginByEmail(AuthRequest authRequest) {
        User user = userService.loginByEmail(authRequest.getId());
        if (user == null) {
            throw new IncorrectEmailException("This Email isn't Registered ");
        }
        authRequest.setUsername(user.getUsername());
        return loginById(authRequest);
    }


    private void authenticate(String id, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, password));
    }

    private ResponseEntity<?> generateLoginResponse(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
        User user = userService.findById(id);
        user.setLastLogin(LocalDateTime.now());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    private void encodePasswordAndAssignRoles(User ut) {
        ut.setPassword(passwordEncoder.encode(ut.getPassword()));
        Role userRole = new Role();
        userRole.setRole("USER");
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        ut.addrole(userRole);
        ut.addrole(adminRole);
        ut.setCreatedAt(LocalDateTime.now());
        ut.setUpdatedAt(LocalDateTime.now());
    }
}
